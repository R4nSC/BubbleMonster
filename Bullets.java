import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/* 銃弾集合のクラス */
public class Bullets {
    private static Bullet[] bullets; // 銃弾の配列

    private static int bulletcnt; // 銃弾集合の中の銃弾の数
    private static double posx; // 銃弾が存在するx座標
    private static double posy; // 銃弾が存在するy座標
    private static int timecnt; // 

    /* コンストラクタ */
    Bullets(double nx,double ny){
	bullets = new Bullet[1000];
	posx = nx;
	posy = ny;
	bulletcnt = 0;
	timecnt = 0;
	addBullet();
    }

    /* 銃弾の集合(配列)を返す */
    public Bullet[] getBullets(){
	return bullets;
    }

    /* 集合の中の銃弾の数を返す */
    public int getBulletCnt(){
	return bulletcnt;
    }

    public double getPosY(){
	return posy;
    }

    /* 銃弾が出てくる */
    public void setPosY(double y){
	posy = y;
    }

    /* 銃弾を追加する(銃弾の長さを伸ばす) */
    public static void addBullet(){
	Bullet new_bullet = new Bullet(posx+13.0,posy);
	bullets[bulletcnt] = new_bullet;
	BubbleMonster.setBulletImage(bullets[bulletcnt].getImageBullet());
	bulletcnt++;
    }

    /* 銃弾の集合を上に移動させる */
    public static void moveBullets(){
	for(int i=0;i<bulletcnt;i++){
	    bullets[i].move();
	}
	timecnt++;
	if(timecnt % 1== 0){
	    addBullet();
	}
    }

    /* 一番上の銃弾が天井に当たったかを判定 */
    public static boolean FirstBulletHitCeiling(){
	if(bullets[0].getImageBullet().getLayoutY() <= 0.0){
	    return true;
	} else {
	    return false;
	}
    }

    /* 表示した銃弾の集団の表示をすべて非表示 */
    public static void removeBullets(){
	for(int i=0;i<bulletcnt;i++){
	    bullets[i].remove();
	    bullets[i] = null;
	}
    }
}
