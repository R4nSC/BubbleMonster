import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/* アイテムクラス */
public class Item {
    private static ImageView item;
    private static Image[] itemsimage;
    private static int itemtype;
    private static int imagenum;

    /* コンストラクタ */
    Item(){
	item = new ImageView();
	itemsimage = new Image[4];
	itemsimage[0] = new Image("img/lifeitem.png");
	itemsimage[1] = new Image("img/scoreitem.png");
	itemsimage[2] = new Image("img/timeitem.png");
        itemsimage[3] = new Image("img/panickitem.png");
	itemtype = 3;
    }
    
    /* アイテムをゲームに表示 */
    public  void setItem(double x,double y){
	Random rnd = new Random();
	 imagenum = rnd.nextInt(4);
	item.setImage(itemsimage[imagenum]);
	item.setLayoutX(x);
	item.setLayoutY(y);
	BubbleMonster.printItem(item);
    }

    public static int getitemtype()   {
       return imagenum;
    }
    
    public static ImageView getImageView(){
       return item;
   }
    public void locateItem(double x,double y){
        BubbleMonster.removeImageView(item);
        item.setLayoutX(x);
	item.setLayoutY(y);
	BubbleMonster.printItem(item);
   }

    public void removeitem(){
        BubbleMonster.removeImageView(item);
    }
}
