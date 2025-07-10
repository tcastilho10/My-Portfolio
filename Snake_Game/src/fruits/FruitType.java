package fruits;


public enum FruitType {
        BERRY("resources/cherry (1).png", 15),
        ORANGE("resources/orange (1).png", 10),
        PINEAPPLE("resources/pineapple (1).png", 10),
        WATERMELON("resources/watermelon.png", 0),
        BLUEBERRY("resources/blueberry.png", 15),
        STRAWBERRY("resources/strawberry (1).png", 20);

        private String imagePath;
        private int fruitScore;

        FruitType(String imagePath, int fruitScore) {
                this.imagePath = imagePath;
                this.fruitScore = fruitScore;

        }

        public String getImagePath() {
                return imagePath;
        }

        public static FruitType getFruitType() {
                int value = (int) (Math.random() *6);

                switch (value) {
                        case 0:
                                return BERRY;

                        case 1:
                                return ORANGE;

                        case 2:
                                return PINEAPPLE;

                        case 3:
                                return STRAWBERRY;

                        case 4:
                                return BLUEBERRY;

                        case 5:
                                return WATERMELON;

                        default:
                                System.out.println("Invalid FruitType");

                }

                return null;
        }

        public int getFruitScore() {
                return fruitScore;
        }

}

















