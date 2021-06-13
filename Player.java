import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/* プレイヤークラス */
public class Player {
    private ImageView playerImage;
    private Image[] charaImage;

    private double rightwall_width;

    /* コンストラクタ */
    Player(double x,double y){
	playerImage = new ImageView();
	charaImage = new Image[4];
	charaImage[0] = new Image("img/player1.png");
	charaImage[1] = new Image("img/player3.png");
	charaImage[2] = new Image("img/player1.png");
	charaImage[3] = new Image("img/player4.png");
	
	playerImage.setImage(charaImage[0]);
	playerImage.setId("player");
	playerImage.setLayoutX(x);
	playerImage.setLayoutY(y);

	rightwall_width = 660.0;
    }

    /* プレイヤーの画像を表示① */
    public ImageView getImageView(){
	return playerImage;
    }
    
    /* プレイヤーの画像を表示② */
    public Image getImage(int num){
	return charaImage[num];
    }

    public double getRightWallWidth(){
	return rightwall_width;
    }

    public void setRightWallWidth(double w){
	rightwall_width = w;
    }

    /* プレイヤーを上に等速で移動(天井が下がるステージで使用) */
    public void up_move(){
	double ny = playerImage.getLayoutY();
	playerImage.setLayoutY(ny-0.1);
    }

    /* プレイヤーを左に移動 */
    public int left_move(Key key,int num){
	double nx = playerImage.getLayoutX();
	/*x 左の壁を超えないようにする */
	if(nx-3.0 > 0){
	    playerImage.setLayoutX(nx-3.0);
	}
	int cnt = key.get_left()%4;
	if(cnt == 1){
	    num++;
	    num %= 2;
	    playerImage.setImage(charaImage[num+2]);
	}
	return num;
    }

    /* プレイヤーを右に移動 */
    public int right_move(Key key,int num){
	double nx = playerImage.getLayoutX();
	/* 右の壁を超えないようにする */
	if(nx+3.0 < rightwall_width){
	    playerImage.setLayoutX(nx+3.0);
	}
	int cnt = key.get_right()%4;
	if(cnt == 1){
	    num++;
	    num %= 2;
	    playerImage.setImage(charaImage[num]);
	}
	return num;
    }
}
