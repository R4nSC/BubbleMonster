import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/* ライフクラス */
public class Life {
    private ImageView[] Life;

    private Image[] lifeimages;
    private Image[] nothingimages;

    private int nowlife;
    private int maxlife;

    /* コンストラクタ */
    Life(int nowl,int maxl){
	Life = new ImageView[maxl];
	lifeimages = new Image[maxl];
	nothingimages = new Image[maxl];
	
	for(int i=0;i<maxl;i++){
	    lifeimages[i] = new Image("img/life.png");
	}
	for(int i=0;i<maxl;i++){
	    nothingimages[i] = new Image("img/nothing2.png");
	}

	nowlife = nowl;
	maxlife = maxl;

	changeLife();
    }

    /* ライフの画像の集合を返す */
    public ImageView[] getLifeArray(){
	return Life;
    }

    /* 現在のライフの数を返す */
    public int getNowLife(){
	return nowlife;
    }
    
    /* ライフの最大値を返す */
    public int getMaxLife(){
	return maxlife;
    }

    /* 現在のライフの数を設定 */
    public void setNowLife(int nowl){
	if(nowl <= 10) nowlife = nowl;
	if(nowl <= 0) nowlife = 0;
    }

    /* ライフの最大値を設定 */
    public void setMaxLife(int maxl){
	maxlife = maxl;
    }

    /* ライフの現在値の表示を変更 */
    public void changeLife(){
	/* 1. ライフ有りの画像を表示 */
	for(int i=0;i<nowlife;i++){
	    ImageView LifeTrueImage = new ImageView();
	    LifeTrueImage.setImage(lifeimages[i]);
	    Life[i] = LifeTrueImage;
	}
	/* 2. ライフ無しの画像を表示 */
	for(int i=nowlife;i<maxlife;i++){
	    ImageView LifeFalseImage = new ImageView();
	    LifeFalseImage.setImage(nothingimages[i]);
	    Life[i] = LifeFalseImage;
	}
    }
}
