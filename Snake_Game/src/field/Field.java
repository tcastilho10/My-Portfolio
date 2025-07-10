package field;

import com.codeforall.online.simplegraphics.graphics.Canvas;
import com.codeforall.online.simplegraphics.graphics.Rectangle;
import com.codeforall.online.simplegraphics.pictures.Picture;


public class Field {

    public static final int PADDING_TOP = 68;
    public static final int PADDING_BOTTOM = 10;
    public static final int PADDING_LEFT = 10;
    public static final int PADDING_RIGHT = 10;
    private int cellSize = 24;
    private int cols;
    private int rows;

    Field field = this;


    public Field(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public void init() {
        Rectangle field = new Rectangle((double)PADDING_LEFT, (double)PADDING_TOP, (double)(this.cols * this.cellSize), (double)(this.rows * this.cellSize));
        field.draw();

        //background
        Picture background = new Picture(PADDING_LEFT,PADDING_TOP, "resources/sand 1440x912.png");
        Canvas.getInstance().show(background);

        //banner
        Picture banner = new Picture(PADDING_LEFT,0,"resources/Snake Banner-01.png");
        Canvas.getInstance().show(banner);

    }

    public int getCellSize() {
        return this.cellSize;
    }

    public int getCols() {
        return this.cols;
    }

    public int getRows() {
        return this.rows;
    }

    public int rowToY(int row) {
        return PADDING_TOP + this.cellSize * row;
    }

    public int columnToX(int column) {
        return PADDING_LEFT + this.cellSize * column;
    }

    /*public int getPadding() {
        return PADDING;
    }*/

}
