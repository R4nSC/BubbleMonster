import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/* 銃弾クラス */
public class Bullet {
    private ImageView bulletimage;

    /* コンストラクタ */
    Bullet(double nx,double ny){
	bulletimage = new ImageView();
	Image Bimage = new Image("img/bullet3.png");
	bulletimage.setImage(Bimage);
	bulletimage.setLayoutX(nx);
	bulletimage.setLayoutY(ny);
    }

    /* 銃弾の画像を返す */
    public ImageView getImageBullet(){
	return bulletimage;
    }

    /* 銃弾を上に動かす */
    public void move(){
	double ny = bulletimage.getLayoutY();
	bulletimage.setLayoutY(Math.max(ny-7.0,0.0));
    }

    /* 銃弾を非表示 */
    public void remove(){
	BubbleMonster.removeImageView(bulletimage);
    }
}
