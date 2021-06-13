import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/* エネミークラス */
public class Enemy {
    private ImageView enemyImage;
    private Image charaImage;

    private double speed;

    private double vx;
    private double vy;

    private int VerticalDir;
    private double HorizontalDir;

    private double width;
    private double height;
    private double enemyangle;
    
    private double jumpheight;
    private double firstheight;

    private double beforePosition;
    private int move_flg;

    private double floor_height;
    private double ceiling_height;

    private double rightwall_width;

    /* 敵のコンストラクタ */
    Enemy(double x,double y,double width,double jumph,double v,double dir){
	enemyImage = new ImageView();
	charaImage = new Image("img/monster.png");
	enemyImage.setImage(charaImage);
	enemyImage.setId("Enemy");
	enemyImage.setLayoutX(x);
	enemyImage.setLayoutY(y);
	enemyImage.setFitWidth(width);	
	enemyImage.setFitHeight(width);
	this.width = width;
	this.height = width;
	jumpheight = jumph;
	firstheight = y;
	vx = v;
	vy = 0.0;
	
	if(y == jumph) {
	    VerticalDir = 1;
	    speed = 0;
	}
	else {
	    VerticalDir = -1;
	    speed = jumpspeed(jumpheight,firstheight);
	}

	enemyangle = 0.0;
	HorizontalDir = dir;
	beforePosition = -1.0;
	move_flg = 0;
	
	floor_height = 400.0;
	ceiling_height = 0.0;

	rightwall_width = 650.0;
    }

    /* 敵の画像を返す① */
    public ImageView getImageView(){
	return enemyImage;
    }

    /* 敵の画像を返す② */
    public Image getImage(){
	return charaImage;
    }

    /* 敵の横幅を返す */
    public double getWidth(){
	return width;
    }

    public double getFloorHeight(){
	return floor_height;
    }

    public double getCeilingHeight(){
	return ceiling_height;
    }
    
    public void setFloorAndCeilingHeight(double fh,double fc){
	floor_height = fh;
	ceiling_height = fc;
    }

    public double getRightWallWidth(){
	return rightwall_width;
    }

    public void setRightWallWidth(double w){
	rightwall_width = w;
    }
    
    /* ある高さからある高さまでの移動する速度を求める */
    public double jumpspeed(double start,double end){
	double nowh = start;
	double nows = 0;
	while(nowh <= end){
	    nows += 0.3;
	    nowh += nows;
	}
	return nows;
    }

    /* 敵の縦横の動き */
    public void move(EnemyType et){
	/* 1. 下についた時に少しだけ止まる */
	if(move_flg != 0){
	    move_flg--;
	} else {
	    
	    /* エネミーの位置を取得 */
	    double nx = enemyImage.getLayoutX();
	    double ny = enemyImage.getLayoutY();

	    /* 2. 横の移動 */
	    if(HorizontalDir == 1.0){ // 右向き
		enemyImage.setLayoutX(Math.min(nx+HorizontalDir*vx,rightwall_width+et.getWgap(width)));
	    } else if(HorizontalDir == -1.0) { // 左向き
		enemyImage.setLayoutX(Math.max(nx+HorizontalDir*vx,0.0));
	    }

	    /* 2.1 ある位置まで移動したら向きを変える */
	    if(nx+HorizontalDir*vx >= rightwall_width+et.getWgap(width)){ // 右→左
		HorizontalDir = -1.0;
	    }
	    if(nx+HorizontalDir*vx <= 0){ // 左→右
		HorizontalDir = 1.0;
	    }

	    /* 3. 縦の移動 */
	    /* 3.1 普通の状態での縦の動き */
	    if(firstheight == jumpheight){
		if(VerticalDir == 1){ // 3.1.1 下向きに動いているとき
		    speed += 0.3;
		    if(ny+speed >= floor_height+et.getHgap(width)){ // 3.1.1.1 下側の床に当たった場合
			beforePosition = ny;
			VerticalDir = -1;
			move_flg = 3;
			enemyImage.setLayoutY(floor_height+et.getHgap(width));
			speed = jumpspeed(et.getJumpPower(width)-ceiling_height,enemyImage.getLayoutY());
		    } else { // 3.1.1.2 下方向にまだ進める場合
			enemyImage.setLayoutY(ny+speed);
		    }
		    
		} else { // 3.1.2 上向きに動いているとき
		    if(ny-speed <= et.getJumpPower(width)-ceiling_height) { // 3.1.2.1 ジャンプする上限の高さまで達した場合
			enemyImage.setLayoutY(et.getJumpPower(width)-ceiling_height);
			speed = 0.0;
		    } else { // 3.1.2.2 まだ上へ進める場合
			enemyImage.setLayoutY(ny-speed);
			speed -= 0.3;
		    }

		    // 3.1.2.1.1 スピードがマイナスになった場合
		    if(speed <= 0.0){
			speed = 0.0;
			VerticalDir = 1; // 向きを下向きに変更
		    }
		    
		}
	    }
	    /* 3.2 分裂したときの少しの跳ね上がりの動き */
	    else {
		if(ny-speed <= jumpheight){
		    speed = 0.0;
		    enemyImage.setLayoutY(jumpheight);
		    VerticalDir = 1;
		    jumpheight = firstheight;
		} else {
		    enemyImage.setLayoutY(ny-speed);
		    speed -= 0.3;
		}
	    }
	}
    }

    /* 敵を回転させる */
    public void enemyRotate(){
	if(enemyangle == 360.0){
	    enemyangle = 0.0;
	} else if(enemyangle < 360.0 && enemyangle >= 0.0){
	    enemyangle += 1.0;
	}
	enemyImage.setRotate(enemyangle);
    }

    /* 敵を表示させなくする(消す) */
    public void removeEnemy(){
	BubbleMonster.removeImageView(enemyImage);
    }
}
