package be.kuleuven;

import java.util.ArrayList;

public class CheckNeighboursInGrid {

    /**
     * This method takes a 1D Iterable and an element in the array and gives back an iterable containing the indexes of all neighbours with the same value as the specified element
     *@return - Returns a 1D Iterable of ints, the Integers represent the indexes of all neighbours with the same value as the specified element on index 'indexToCheck'.
     *@param grid - This is a 1D Iterable containing all elements of the grid. The elements are integers.
     *@param width - Specifies the width of the grid.
     *@param height - Specifies the height of the grid (extra for checking if 1D grid is complete given the specified width)
     *@param indexToCheck - Specifies the index of the element which neighbours that need to be checked
     */
    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid,int width, int height, int indexToCheck){

        ArrayList<Integer> neighbours = new ArrayList<>();
        ArrayList<Integer> gridList = new ArrayList<>();
        for (Integer x : grid){
            gridList.add(x);
        }
        if (indexToCheck % width != 0) {
            if (gridList.get(indexToCheck - 1).equals(gridList.get(indexToCheck))) {
                neighbours.add(indexToCheck - 1);
            }
        }
        if ((indexToCheck + 1) % width != 0) {
            if (gridList.get(indexToCheck + 1).equals(gridList.get(indexToCheck))) {
                neighbours.add(indexToCheck + 1);
            }
        }
        if (indexToCheck - width >= 0) {
            if (gridList.get(indexToCheck - width).equals(gridList.get(indexToCheck))) {
                neighbours.add(indexToCheck - width);
            }
            if (indexToCheck % width !=0) {
                if (gridList.get(indexToCheck - width - 1).equals(gridList.get(indexToCheck))) {
                    neighbours.add(indexToCheck - width - 1);
                }
            }
            if ((indexToCheck + 1) % width !=0) {
                if (gridList.get(indexToCheck - width + 1).equals(gridList.get(indexToCheck))) {
                    neighbours.add(indexToCheck - width + 1);
                }
            }
        }
        if (indexToCheck + width < width * height) {
            if (gridList.get(indexToCheck + width).equals(gridList.get(indexToCheck))) {
                neighbours.add(indexToCheck + width);
            }
            if (indexToCheck % width !=0) {
                if (gridList.get(indexToCheck + width - 1).equals(gridList.get(indexToCheck))) {
                    neighbours.add(indexToCheck + width - 1);
                }
            }
            if ((indexToCheck + 1) % width !=0) {
                if (gridList.get(indexToCheck + width + 1).equals(gridList.get(indexToCheck))) {
                    neighbours.add(indexToCheck + width + 1);
                }
            }
        }
        return neighbours;
    }
}
