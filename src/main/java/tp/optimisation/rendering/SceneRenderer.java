package tp.optimisation.rendering;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import tp.optimisation.BinPacking;
import tp.optimisation.Dataset;

public class SceneRenderer extends Application {

    private String datasetFile;
    private BinPacking bp;
    private BinPackingRenderer bpRenderer;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root.getChildren().add(world);
        world.setDepthTest(DepthTest.ENABLE);

        // buildScene();
        buildCamera();
        buildAxes();
        buildLight();

        SubScene subScene = new SubScene(root, 800, 800, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.GREY);
        subScene.setCamera(camera);

        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 1000, 800);

        pane.setCenter(subScene);
        BorderPane rightPane = new BorderPane();
        rightPane.setPrefSize(200, 800);

        ChoiceBox<String> datasets = new ChoiceBox<>(FXCollections.observableArrayList(
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
        ));
        datasets.setValue("01");
        datasets.setPrefSize(150, 30);
        datasets.setOnAction(e -> {
            changeDataset(datasets.getValue());
        });
        changeDataset(datasets.getValue());
        rightPane.setTop(datasets);

        Button readDatasetButton = new Button("Read dataset");
        readDatasetButton.setOnAction(e -> {
            world.getChildren().clear();
            bp = new BinPacking(Dataset.fromFile(datasetFile));
            bpRenderer = new BinPackingRenderer(bp);
            bpRenderer.renderInto(world);
            bpRenderer.registerEvents(scene);
        });
        readDatasetButton.setPrefSize(150, 50);
        rightPane.setCenter(readDatasetButton);

        Button nextIterationButton = new Button("Next iteration");
        nextIterationButton.setOnAction(e -> {
            bp.getNextIteration();
            bpRenderer.addNextRow();
        });
        nextIterationButton.setPrefSize(150, 50);
        rightPane.setBottom(nextIterationButton);

        pane.setRight(rightPane);

        handleKeyboard(scene);
        handleMouse(scene);

        primaryStage.setTitle("Bin Packing 2D");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void changeDataset(String value) {
        datasetFile = "data/binpacking2d-" + value + ".bp2d";
    }

    private void buildCamera() {
        System.out.println("buildCamera()");
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
        System.out.println("buildAxes()");
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
        System.out.println("buildLight()");
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
