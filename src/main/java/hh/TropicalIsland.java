package hh;

public class TropicalIsland {
    public int getWaterVolume(Integer[][] island) {
        int height = island.length;
        int width = island[0].length;
        int amountOfWater = 0;

        if (height > 1 || width > 1) {
            //init
            int maxValue = getMaxValue(island);
            Integer[][] rainedIsland = new Integer[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                        rainedIsland[i][j] = island[i][j];
                    } else {
                        rainedIsland[i][j] = maxValue;
                    }
                }
            }

            boolean isChanged = true;

            while (isChanged) {
                isChanged = false;
                for (int i = 1; i < height - 1; i++) {
                    for (int j = 1; j < width - 1; j++) {
                        int first = rainedIsland[i - 1][j];
                        int second = rainedIsland[i + 1][j];
                        int third = rainedIsland[i][j - 1];
                        int fourth = rainedIsland[i][j + 1];
                        int minValue = Math.min(first, Math.min(second, Math.min(third, fourth)));

                        if (rainedIsland[i][j] > minValue && !rainedIsland[i][j].equals(island[i][j])) {
                            if (minValue > island[i][j]) {
                                rainedIsland[i][j] = minValue;
                            } else {
                                rainedIsland[i][j] = island[i][j];
                            }
                            isChanged = true;
                        }

                    }
                }
            }

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    amountOfWater += rainedIsland[i][j] - island[i][j];
                }
            }
        }
        return amountOfWater;
    }


    public static int getMaxValue(Integer[][] numbers) {
        int maxValue = numbers[0][0];
        for (int j = 0; j < numbers.length; j++) {
            for (int i = 0; i < numbers[j].length; i++) {
                if (numbers[j][i] > maxValue) {
                    maxValue = numbers[j][i];
                }
            }
        }
        return maxValue;
    }

    public static void main(String[] args) {
        final Integer[][] matrix1 = {
                {5, 3, 4, 5},
                {6, 2, 1, 4},
                {3, 1, 1, 4},
                {8, 5, 4, 3}
        };

        final Integer[][] matrix2 = {
                {4, 5, 4},
                {3, 1, 5},
                {5, 4, 1}
        };

        final Integer[][] matrix3 = {
                {2, 2, 2},
                {2, 1, 2},
                {2, 1, 2},
                {2, 1, 2}
        };

        TropicalIsland island1 = new TropicalIsland();

        System.out.println(island1.getWaterVolume(matrix1));
        System.out.println(island1.getWaterVolume(matrix2));
        System.out.println(island1.getWaterVolume(matrix3));
    }

}
