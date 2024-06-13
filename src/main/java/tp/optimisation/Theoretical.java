package tp.optimisation;

public class Theoretical {

    public static void main(String[] args) {
        String[] files = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"};

        for (String file : files) {
            String datasetFile = "data/binpacking2d-" + file + ".bp2d";
            Dataset dataset = Dataset.fromFile(datasetFile);

            System.out.println();
            System.out.println(datasetFile +":");
            int binSize = dataset.getBinHeight() * dataset.getBinWidth();
            System.out.println("Taille d'une bin : " + binSize + " m²");
            int totalSizeItem = 0;
            for (Item i : dataset.getItems()) {
                totalSizeItem += i.getWidth() * i.getHeight();
            }
            System.out.println("Surface de l'ensemble des items : " + totalSizeItem + " m²");
            System.out.println("Nombre de bin minimum : " + (totalSizeItem / binSize + 1));
        }

    }
}
