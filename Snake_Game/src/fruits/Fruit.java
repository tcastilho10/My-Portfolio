package fruits;

import com.codeforall.online.simplegraphics.pictures.Picture;
import field.Field;

public class Fruit {

    private Picture fruitPic;
    private FruitType fruitType;
    private int col;
    private int row;
    private Field field;
    private int fruitScore;

    public Fruit(Field field){
        this.field = field;
        this.fruitType = FruitType.getFruitType();
        this.col=(int)(Math.random()*field.getCols());
        this.row=(int)(Math.random()*field.getRows());
        this.fruitPic = new Picture(field.columnToX(col),field.rowToY(row), fruitType.getImagePath());
        fruitPic.draw();
        fruitScore = fruitType.getFruitScore();
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Picture getFruitPic() {
        return fruitPic;
    }

    public void setFruitPic(Picture fruitPic) {
        this.fruitPic = fruitPic;
    }

    public int getFruitScore(){
        return fruitScore;
    }
}
