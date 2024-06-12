package tp.optimisation.rendering;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tp.optimisation.BinPacking;
import tp.optimisation.Dataset;
import tp.optimisation.metaheuristics.*;
import tp.optimisation.neighbours.SwitchNeighboursCalculator;

public class SceneRenderer extends Application {

    private Scene scene;

    private String datasetFile;
    private BinPacking bp;
    private BinPackingRenderer bpRenderer;

    private Text nbBinsText;
    private Text nbIterationsText;

    final Group root = new Group();
    final XForm axisGroup = new XForm();
    final XForm lightGroup = new XForm();
    final XForm world = new XForm();

    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final XForm cameraXForm = new XForm();
    final XForm cameraXForm2 = new XForm();
    final XForm cameraXForm3 = new XForm();

    private static final double CAMERA_INITIAL_DISTANCE = -900;
    private static final double CAMERA_INITIAL_X_ANGLE = 35.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 135.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    private static final double CONTROL_MULTIPLIER = 0.75;
    private static final double SHIFT_MULTIPLIER = 5.0;
    private static final double MOUSE_SPEED = 0.2;
    private static final double ROTATION_SPEED = 1.5;
    private static final double TRACK_SPEED = 3;
    private static final double SCROLL_SPEED = 2;

    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;

    public void startApplication() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane globalPane = new BorderPane();
        scene = new Scene(globalPane, 1000, 800);

        globalPane.setCenter(setUp3DScene());
        globalPane.setRight(setUpCommands());

        handleKeyboard(scene);
        handleMouse(scene);

        primaryStage.setTitle("Bin Packing 2D");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private SubScene setUp3DScene(){
        root.getChildren().add(world);
        world.setDepthTest(DepthTest.ENABLE);

        // buildScene();
        buildCamera();
        buildAxes();
        buildLight();

        SubScene subScene = new SubScene(root, 800, 800, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.GREY);
        subScene.setCamera(camera);

        return subScene;
    }

    private Pane setUpCommands(){
        BorderPane commandsPane = new BorderPane();
        commandsPane.setPrefSize(200, 800);

        commandsPane.setTop(selectDatabasePane());
        commandsPane.setCenter(changeParametersPane());
        commandsPane.setBottom(updateStatePane());

        return commandsPane;
    }

    private Pane selectDatabasePane() {
        HBox selectDatabasePane = new HBox();
        selectDatabasePane.setSpacing(10);
        selectDatabasePane.setPadding(new Insets(10, 10, 10, 10));

        ChoiceBox<String> datasets = new ChoiceBox<>(FXCollections.observableArrayList(
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
        ));
        datasets.setValue("01");
        datasets.setPrefSize(90, 30);
        datasets.setOnAction(e -> changeDataset(datasets.getValue()));
        changeDataset(datasets.getValue());

        selectDatabasePane.getChildren().add(datasets);

        Button readDatasetButton = new Button("Import");
        readDatasetButton.setOnAction(e -> {
            world.getChildren().clear();
            bp = new BinPacking(Dataset.fromFile(datasetFile));
            bpRenderer = new BinPackingRenderer(bp);
            bpRenderer.renderInto(world);
            bpRenderer.registerEvents(scene);
            bp.setRunOnUpdate(() -> bpRenderer.addNextRow());
            updateValues();
        });
        readDatasetButton.setPrefSize(90, 30);
        selectDatabasePane.getChildren().add(readDatasetButton);

        return selectDatabasePane;
    }

    private Pane changeParametersPane() {
        VBox parametersPane = new VBox();
        parametersPane.setSpacing(10);
        parametersPane.setPadding(new Insets(10, 10, 10, 10));
        parametersPane.setAlignment(Pos.CENTER_LEFT);

        ToggleGroup radioGroup = new ToggleGroup();

        RadioButton rbHill = new RadioButton("Hill Climbing");
        rbHill.setToggleGroup(radioGroup);
        rbHill.setUserData(new HillClimbingMetaheuristic(new SwitchNeighboursCalculator()));
        rbHill.setSelected(true);
        RadioButton rbTabou = new RadioButton("Tabou");
        rbTabou.setToggleGroup(radioGroup);
        rbTabou.setUserData(new TabouMetaheuristic(new SwitchNeighboursCalculator()));
        RadioButton rbGenetic = new RadioButton("Genetic");
        rbGenetic.setToggleGroup(radioGroup);
        rbGenetic.setUserData(new GeneticMetaheuristic(new SwitchNeighboursCalculator()));
        RadioButton rbAnneal = new RadioButton("Simulating Annealing");
        rbAnneal.setToggleGroup(radioGroup);
        rbAnneal.setUserData(new SimulatingAnnealingMetaheuristic(new SwitchNeighboursCalculator()));

        VBox paramByMetaheuristic = new VBox();
        paramByMetaheuristic.setSpacing(5);
        paramByMetaheuristic.setPadding(new Insets(5, 5, 5, 5));
        paramByMetaheuristic.setAlignment(Pos.CENTER_RIGHT);

        radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                Metaheuristic metaheuristic = (Metaheuristic) newValue.getUserData();
                metaheuristic.reset();
                changeParameters(paramByMetaheuristic, metaheuristic);
                bp.setMetaheuristic(metaheuristic);
                updateValues();
                bpRenderer.reset();
            }
        });

        parametersPane.getChildren().addAll(rbHill, rbTabou, rbGenetic, rbAnneal);
        parametersPane.getChildren().add(paramByMetaheuristic);
        return parametersPane;
    }

    private void changeParameters(VBox box, Metaheuristic metaheuristic) {
        box.getChildren().clear();
        Button buttonSave = new Button("Save");
        if (metaheuristic instanceof HillClimbingMetaheuristic) {
            return;
        } else if (metaheuristic instanceof TabouMetaheuristic t) {
            HBox line1 = new HBox();
            Text textLine1 = new Text("List size: ");
            TextField textFieldLine1 = new TextField();
            textFieldLine1.setText(String.valueOf(t.getMaxSizeTabouList()));

            buttonSave.setOnAction(actionEvent -> {
                t.setMaxSizeTabouList(Integer.parseInt(textFieldLine1.textProperty().getValue()));
            });
            line1.getChildren().addAll(textLine1, textFieldLine1);
            box.getChildren().add(line1);
        } else if (metaheuristic instanceof GeneticMetaheuristic g) {
            HBox line1 = new HBox();
            Text textLine1 = new Text("Best Solutions: ");
            TextField textFieldLine1 = new TextField();
            textFieldLine1.setText(String.valueOf(g.getNbBestSolutions()));
            line1.getChildren().addAll(textLine1, textFieldLine1);
            HBox line2 = new HBox();
            Text textLine2 = new Text("Mutation Rate: ");
            TextField textFieldLine2 = new TextField();
            textFieldLine2.setText(String.valueOf(g.getMutationRate()));

            buttonSave.setOnAction(actionEvent -> {
                g.setNbBestSolutions(Float.parseFloat(textFieldLine1.textProperty().getValue()));
                g.setMutationRate(Float.parseFloat(textFieldLine2.textProperty().getValue()));
            });
            line2.getChildren().addAll(textLine2, textFieldLine2);
            box.getChildren().addAll(line1, line2);

        } else if (metaheuristic instanceof SimulatingAnnealingMetaheuristic s) {
            HBox line1 = new HBox();
            Text textLine1 = new Text("Cooling Rate: ");
            TextField textFieldLine1 = new TextField();
            textFieldLine1.setText(String.valueOf(s.getCoolingRate()));

            buttonSave.setOnAction(actionEvent -> {
                s.setCoolingRate(Float.parseFloat(textFieldLine1.textProperty().getValue()));
            });
            line1.getChildren().addAll(textLine1, textFieldLine1);
            box.getChildren().add(line1);
        }
        box.getChildren().add(buttonSave);
    }

    private Pane updateStatePane() {
        VBox updateStatePane = new VBox();
        updateStatePane.setSpacing(10);
        updateStatePane.setPadding(new Insets(10, 10, 10, 10));
        updateStatePane.setAlignment(Pos.CENTER);

        nbBinsText = new Text();
        nbIterationsText = new Text();
        updateStatePane.getChildren().addAll(nbBinsText, nbIterationsText);


        Button nextIterationButton = new Button("Next iteration");
        nextIterationButton.setOnAction(e -> {
            bp.getNextIteration();
            updateValues();
            bpRenderer.registerEvents(scene);
        });
        nextIterationButton.setPrefSize(150, 30);

        updateStatePane.getChildren().add(nextIterationButton);

        Button processButton = new Button("10 iterations");
        processButton.setOnAction(e -> {
            bp.processUntilConvergence();
            updateValues();
            bpRenderer.registerEvents(scene);
        });
        processButton.setPrefSize(150, 30);

        updateStatePane.getChildren().add(processButton);

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            bp.reset();
            updateValues();
            bpRenderer.reset();
        });
        resetButton.setPrefSize(150, 30);

        updateStatePane.getChildren().add(resetButton);

        return updateStatePane;
    }

    private void updateValues()
    {
        nbBinsText.setText("Nb bins: " + bp.getBins().size());
        nbIterationsText.setText("Nb iterations: " + bp.getNbIterations());
    }

    private void changeDataset(String value) {
        datasetFile = "data/binpacking2d-" + value + ".bp2d";
    }

    private void buildCamera() {
        root.getChildren().add(cameraXForm);
        cameraXForm.getChildren().add(cameraXForm2);
        cameraXForm2.getChildren().add(cameraXForm3);
        cameraXForm3.getChildren().add(camera);
        cameraXForm3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXForm.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXForm.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }


    private void buildAxes() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(false);
        root.getChildren().addAll(axisGroup);
    }

    private void buildLight() {
        PointLight pointLight = new PointLight();
        pointLight.setColor(Color.WHITE);
        pointLight.setConstantAttenuation(3);

        AmbientLight ambientLight = new AmbientLight(Color.WHITE);

        lightGroup.getChildren().addAll(ambientLight, pointLight);
        lightGroup.setTranslate(500, 1000, 300);
        root.getChildren().addAll(lightGroup);
    }

    private void handleMouse(Scene scene) {
        scene.setOnMousePressed(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnScroll(se -> camera.setTranslateZ(camera.getTranslateZ() + se.getDeltaY()*SCROLL_SPEED));
        scene.setOnMouseDragged(me -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            double modifier = 1.0;

            if (me.isControlDown()) {
                modifier = CONTROL_MULTIPLIER;
            }
            if (me.isShiftDown()) {
                modifier = SHIFT_MULTIPLIER;
            }
            if (me.isPrimaryButtonDown()) {
                cameraXForm.ry.setAngle(cameraXForm.ry.getAngle() - mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED);
                cameraXForm.rx.setAngle(cameraXForm.rx.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);
            }
            else if (me.isSecondaryButtonDown() || me.isMiddleButtonDown()) {
                cameraXForm2.t.setX(cameraXForm2.t.getX() + mouseDeltaX*MOUSE_SPEED*modifier*TRACK_SPEED);
                cameraXForm2.t.setY(cameraXForm2.t.getY() + mouseDeltaY*MOUSE_SPEED*modifier*TRACK_SPEED);
            }
        });
    }

    private void handleKeyboard(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z:
                    cameraXForm2.t.setX(0.0);
                    cameraXForm2.t.setY(0.0);
                    camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                    cameraXForm.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                    cameraXForm.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                    break;
                case X:
                    axisGroup.setVisible(!axisGroup.isVisible());
                    break;
            }
        });
    }

}
