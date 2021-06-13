import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Bounds;
import java.util.Random;

public class GameController extends AnimationTimer {

    /* ----- ゲーム要素　 -------------------- */
    private GraphicsContext gc;
    private Key key;
    
    private Player chara;
    private Enemy[] enemys;
    private Life playerlife;
    private Bullets bullets;
    /* ----- ここまで　　 -------------------- */
    
    /* ----- イメージ関係 -------------------- */
    private int imagenum = 0;
    private Image ready;
    private Image defeated;
    private Image limit;
    private Image gameclear;
    private ImageView putyun;
    /* ----- ここまで　　 -------------------- */

    /* ----- エネミー関係 -------------------- */
    private EnemyType enemytype;
    private int enemycnt;
    private int hitenemycnt;
    private boolean[] enemydeadflg = new boolean[100];
    /* ----- ここまで　　 -------------------- */

    /* ----- タイマー関係 -------------------- */
    private int timecount = 0;
    private int waittime;
    private int putyuntimer;
    /* ----- ここまで　　 -------------------- */

    /* ----- ステージ関係 -------------------- */
    private StagesInfo stageinfo;
    private int nowstagenum;
    private int laststage;
    /* ----- ここまで　　 -------------------- */
    
    /* ----- 　その他　　 -------------------- */
    private int gamestate = 0;
    private int nowscore = 0;
    private boolean bulletflg;
    
    private int enemyjudge;

    private double middleImageX;
    private double middleImageY;

    private double rightwall;
    private boolean widthchange;
    private double vx;

    //変更ここから
    private int itemjudge;//アイテムが出現する確率
    private int itemjudge2 = 0;//アイテムが現在出現しているかどうかの判断
    private double keepx;
    private double keepy;
    private int itemgetjudge = 0;//アイテムを取得可能かどうかの判断
    private Item item = new Item();
    private int itemtime = 0;
    private int prickjudge = 0;
    private int reversejudge = 0;
    //変更ここまで
    /* ----- ここまで　　 -------------------- */


    /* GameControllerのコンストラクタ */
    GameController(GraphicsContext gc,Key key){
	/* 1. 使用する要素の準備 */
	this.gc = gc;
	this.key = key;

	/* 1.1 プレイヤーの体力 */
	playerlife = new Life(5,10);

	/* 1.2 状況を表す画像 */
	ready = new Image("img/ready.png");
	defeated = new Image("img/defeated.png");
	limit = new Image("img/limit.png");
	gameclear = new Image("img/clear.png");

	/* 1.3 エネミーの大きさによる数値 */
	enemytype = new EnemyType();

	/* 1.4 ステージの初期設定 */
	nowstagenum = 1;
	laststage = 10;
	
	/* 1.5 ゲームの初期化(新しくステージを作成) */
	nowscore = 0;
	GameInit();
    }

    /* 新たなステージを作成するための初期化 */
    public void GameInit(){
	/* 1. プレイヤーを生成 */
	chara = new Player(350.0,400.0);

	/* 2. 現在のステージのエネミーを生成 */
	stageinfo = new StagesInfo();
	enemys = stageinfo.getStageInfo(nowstagenum);
	enemycnt = stageinfo.getFirstEnemycnt(nowstagenum);

	/* 3. ゲーム画面とタイムゲージを一度リセットする */
	BubbleMonster.GameFieldClear();
	BubbleMonster.resetTimeGauge();

	/* 4. 画面上にプレイヤー、エネミー、体力を表示 */
	BubbleMonster.setCharaImage(chara.getImageView());
	for(int i=0;i<enemycnt;i++){
	    BubbleMonster.setCharaImage(enemys[i].getImageView());
	}
	BubbleMonster.setLifeImage(playerlife.getLifeArray(),
				   playerlife.getNowLife(),
				   playerlife.getMaxLife());
	
	/* 5.各種初期設定 */
	bulletflg = false;
	waittime = 100;
	hitenemycnt = 0;
	middleImageX = 200.0;
	middleImageY = 200.0;
	vx = 2.0;

	for(int i=0;i<100;i++){
	    enemydeadflg[i] = true;
	}

	BubbleMonster.setStageLabel(nowstagenum);
	
	putyuntimer = -1;
	putyun = new ImageView(new Image("img/putyun.png"));
	putyun.setVisible(false);
	BubbleMonster.setPutyunImage(putyun);
	
	BubbleMonster.InitGameField(700.0,450.0,50.0,0.0);
	if(nowstagenum == 7){
	    BubbleMonster.InitGameField(700.0,200.0,50.0,250.0);
	    BubbleMonster.removeImageView(chara.getImageView());
	    chara = new Player(350.0,150.0);
	    BubbleMonster.setCharaImage(chara.getImageView());
	    double gfh = BubbleMonster.getGameFieldHeight();
	    for(int i=0;i<enemycnt;i++){
		enemys[i].setFloorAndCeilingHeight(150.0,400.0-150.0);
	    }
	    middleImageY = 80.0;
	}
	if(nowstagenum == 8) {
	    widthchange = true;
	    rightwall = 650.0;
	}

	if(nowstagenum == 10){
	    vx = 5.0;
	}
    }
    
    @Override
    /* ものすごい速さで実行を繰り返してくれる素晴らしい関数様 */
    public void handle(long time){
	gc.clearRect(0,0,693,592);

	/* キー操作がされた回数をどんどんカウントしていく */
	key.calc_key_count();
	
	if (gamestate == 0) { // ゲームが始まる前
	    /* 「Are you ready?」を表示 */
	    printMiddleImage(ready,middleImageX,middleImageY);
	    if(waittime == 0){
		gamestate = 1;
		BubbleMonster.removeStateImage();
	    }	    
	} else if (gamestate == 1) { // ゲームを行っている状況
	    /* 1. 敵を動かす */
	    for(int i=0;i<enemycnt;i++){
		if(enemydeadflg[i]){
		    enemys[i].move(enemytype);
		    enemys[i].enemyRotate();
		}
	    }
	    /* 2. 弾に関する処理 */
	    if(bulletflg){
		/* 2.1 弾の出る高さを調整 */
		bullets.setPosY(BubbleMonster.getGameFieldHeight()-16.0);
		/* 2.2 弾を動かす */
		bullets.moveBullets();
		/* 2.3 弾と敵の衝突判定 */
		isIntersects_Bullets_Enemy();
		/* 2.4 弾と天井の衝突判定(天井に当たったら消える) */
		if(bulletflg && bullets.FirstBulletHitCeiling()){
		    bullets.removeBullets();
		    bullets = null;
		    bulletflg = false;
		}
	    }
	    /* 3. プレイヤーと敵の衝突判定*/
	    isIntersects_Player_Enemy();

	    /* 4. エフェクトの表示判定 */
	    putyunTimerCount();

	    /* 5. キー操作の判定 */
	    if (reversejudge == 0){
                keyJudge();
	    }
	    else if(reversejudge == 1){
                reversekeyjudge();
	    }

	    
	    if (itemjudge2 == 1){
		isIntersects_Player_Item();
		if (keepy <= BubbleMonster.getGameFieldHeight()-27){
		    keepy += 1;
		    if (keepy <= BubbleMonster.getGameFieldHeight()-27)
			item.locateItem(keepx,keepy);
		    else if(keepy > BubbleMonster.getGameFieldHeight()-27)
			item.locateItem(keepx,BubbleMonster.getGameFieldHeight()-27);
		}
		
		else if ( keepy >= BubbleMonster.getGameFieldHeight()-27){
		    itemtime++;
		    if (itemtime == 200){
			itemgetjudge = 1;
			itemjudge2 = 0;
			item.removeitem();
		    }
		}
	    }
	    
	    /* 6. 残り時間の更新 */
	    timelimit();
	    
	    /* 特. ステージ6での特殊な動作 */
	    if(nowstagenum == 6){
		BubbleMonster.ceildown(chara);
		putyun.setLayoutY(putyun.getLayoutY()-0.1);
		for(int i=0;i<enemycnt;i++){
		    if(enemydeadflg[i]){
			double nf = enemys[i].getFloorHeight();
			double nc = enemys[i].getCeilingHeight();
			enemys[i].setFloorAndCeilingHeight(nf-0.1,nc+0.1);
		    }
		}
	    }

	    if(nowstagenum == 8){
		double nw = BubbleMonster.getGameFieldWidth();
		double nh = BubbleMonster.getGameFieldHeight();
		double nx = BubbleMonster.getGameFieldLayoutX();
		if(widthchange) BubbleMonster.InitGameField(nw-1.0,nh,nx+0.5,0.0);
		else BubbleMonster.InitGameField(nw+1.0,nh,nx-0.5,0.0);
		
		if(widthchange) middleImageX -= 0.5;
		else middleImageX += 0.5;

		if(widthchange) rightwall--;
		else rightwall++;

		double cx = chara.getImageView().getLayoutX();
		if(widthchange) chara.getImageView().setLayoutX(cx-0.5);
		else chara.getImageView().setLayoutX(cx+0.5);
				    
		double pr = chara.getRightWallWidth();
		if(widthchange) chara.setRightWallWidth(rightwall+10.0);
		else chara.setRightWallWidth(rightwall+10.0);
		if(chara.getImageView().getLayoutX() >= rightwall+10.0){
		    chara.getImageView().setLayoutX(rightwall+10.0-3.0);
		}
		
		for(int i=0;i<enemycnt;i++){
		    if(enemydeadflg[i]){
			double er = enemys[i].getRightWallWidth();
			if(widthchange) enemys[i].setRightWallWidth(rightwall);
			else enemys[i].setRightWallWidth(rightwall);
		    }
		}
		
		if(nw-1.0 <= 400.0) widthchange = false;
		if(nw+1.0 >= 700.0) widthchange = true;
	    }
	    
	    
	} else if(gamestate == 5){ // 時間がオーバーした場合
	    /* 「Time Up!!!」を表示 */
	    printMiddleImage(limit,middleImageX,middleImageY);
	    if(waittime == 0){
		gamestate = 0;
		GameInit();
	    }
	} else if(gamestate == 7){ // 敵に当たった場合
	    /* 「Defeated!!!」を表示 */
	    printMiddleImage(defeated,middleImageX,middleImageY);
	    if(waittime == 0){
		gamestate = 0;
		GameInit();
	    }
	} else if(gamestate == 10){ // プレイヤーの体力がなくなった場合
	    /* ゲームオーバーの画像をゲーム画面に表示 */
	    GameOver();
	    //Music.musicstop();
	} else if(gamestate == 15){ // ステージをクリア(敵をすべて撃破)
	    /* 1. 残っている時間をスコアに加える */
	    if(timecount!=1600){
		int down_gauge = Math.min(1600-timecount,10);
		timecount += down_gauge;
		BubbleMonster.changeTimeGauge(0.5*(double)down_gauge);
		nowscore += down_gauge;
		BubbleMonster.setScoreLabel(nowscore);
	    } else { // 2. 時間をスコアに加えたらクリアの文字を表示
		printMiddleImage(gameclear,middleImageX,middleImageY);
	    }
	    if(waittime == 0){ // 3. 次のステージへ移動
		nowstagenum++;
		timecount = 0;
		if(nowstagenum <= laststage){ // 3.1 まだステージが続く
		    gamestate = 0;
		} else { // 3.2 全ステージクリア
		    gamestate = 20;
		}
		GameInit();
	    }
	}
    }
    
    /* ゲームオーバーしたときに行う処理 */
    public void GameOver(){
	if(waittime == 100){
	    BubbleMonster.InitGameField(700.0,450.0,50.0,0.0);
	    BubbleMonster.GameFieldClear();
	    ImageView image = new ImageView(new Image("img/gameover.png"));
	    image.setId("GameOverImage");
	    BubbleMonster.setGameOverImage(image);
	    waittime--;
	} else if(waittime == 0){
	    gamestate = 20;
	    BubbleMonster.operateTitleObject(true);
	    BubbleMonster.operateGameObject(false);
	} else if(waittime <= 100){
	    waittime--;
	}
    }
       

    /* 真ん中に表示される文字画像のタイマー関数 */
    public void printMiddleImage(Image image,double x,double y){;
	if(waittime == 100){
	    BubbleMonster.ChangeStateImage(image,x,y);
	    BubbleMonster.printStateImage();
	    waittime--;
	} else if(waittime == 0){
	    BubbleMonster.removeStateImage();
	} else if(waittime <= 100){
	    waittime--;
	} 
    }

    /* キー操作を監視する */
    public void keyJudge(){
	/* 1. 何も押されていない場合 */
	if(key.get_left() == 0 && key.get_right() == 0){
	    chara.getImageView().setImage(chara.getImage(0)); // プレイヤーを正面にする
	    imagenum = 0;
	}

	/* 2. スペースキーが押された & いま銃弾が出ていない場合 */
	if(key.get_space() == 1 && bulletflg == false) {
	    bullets = new Bullets(chara.getImageView().getLayoutX(),
				  BubbleMonster.getGameFieldHeight()-16.0); // 銃弾を生成
	    bulletflg = true;
	}

	/* 3. 左の方向キーが押された場合 */
	if(key.get_left() > 0) imagenum = chara.left_move(key,imagenum); // 左に移動

	/* 4. 右の方向キーが押された場合 */
	if(key.get_right() > 0) imagenum = chara.right_move(key,imagenum); // 右に移動
    }

    public void reversekeyjudge(){//混乱時
	if(key.get_left() == 0 && key.get_right() == 0){
	    chara.getImageView().setImage(chara.getImage(0)); // プレイヤーを正面にする
	    imagenum = 0;
	}
	
	if(key.get_space() == 1 && bulletflg == false) {
	    bullets = new Bullets(chara.getImageView().getLayoutX(),
				  BubbleMonster.getGameFieldHeight()-16.0); // 銃弾を生成
	    bulletflg = true;
	}

	/* 3. 左の方向キーが押された場合 */
	if(key.get_left() > 0) imagenum = chara.right_move(key,imagenum); // 左に移動

	/* 4. 右の方向キーが押された場合 */
	if(key.get_right() > 0) imagenum = chara.left_move(key,imagenum); // 右に移動
    }
    
    /* 時間制限機能をつけるためのタイマー関数 */
    public void timelimit(){
        timecount++;
	BubbleMonster.changeTimeGauge(0.5);
        if (timecount == 1600){ // 制限時間を30秒に設定
	    int nl = playerlife.getNowLife();
	    waittime = 100;
	    nl--;
	    playerLifeChange(nl);
	    BubbleMonster.setLifeImage(playerlife.getLifeArray(),nl,playerlife.getMaxLife());    
	    isLifeRemain(nl,5);
	}
    }

    /* プレイヤーの体力値を変化させる */
    public void playerLifeChange(int life){
	playerlife.setNowLife(life);
	playerlife.changeLife();
    }

    /* プレイヤーの体力がまだ残っているのか判定 */
    public void isLifeRemain(int life,int statenum){
	if (life > 0) {
	    timecount = 0;
	    gamestate = statenum;
	    waittime = 100;
	} else { 
	    gamestate = 10;
	    waittime = 100;
	}
    }
    
    /* プレイヤーが出した弾と敵の当たり判定① */
    public void isIntersects_Bullets_Enemy(){
	for(int i=0;i<enemycnt;i++){
	    if(enemydeadflg[i]){
		if(isIntersects_Bullets_Enemy(enemys[i],i)){
		    return;
		}
	    }
	}
    }
    
    /* プレイヤーが出した弾と敵の当たり判定② */
    public boolean isIntersects_Bullets_Enemy(Enemy enemy,int num){
	ImageView ei = enemy.getImageView();
	Bounds enemyBound = ei.getBoundsInParent();
	Bullet[] bls = bullets.getBullets();
	for(int i=0;i<bullets.getBulletCnt();i++){
	    ImageView bi = bls[i].getImageBullet();
	    Bounds bulletBound = bi.getBoundsInParent();
	    if(enemyBound.intersects(bulletBound)){
		bullets.removeBullets();
		bullets = null;
		bulletflg = false;
		nowscore += enemytype.getEnemyScore(enemy.getWidth());
		BubbleMonster.setScoreLabel(nowscore);
		Split_Enemy(enemy);
		
		itemRandomAppear(enemy);
		
		enemy.removeEnemy();
		enemy = null;
		hitenemycnt++;
		enemydeadflg[num] = false;
		if(enemycnt == hitenemycnt){
		    gamestate = 15;
		    waittime = 100;
		}
		return true;
	    }
	}
	return false;
    }

    public void itemRandomAppear(Enemy enemy){
	Random rnd = new Random();
	itemjudge  = rnd.nextInt(99);
	if (itemjudge >= 0 && itemjudge2 == 0)   {   //20%でアイテムゲット（アイテムひとつ出現中は出ないよう設定）
	    itemgetjudge =0;//アイテム取得可能
	    itemjudge2 = 1;//アイテム出現不可
	    double nx = enemy.getImageView().getLayoutX();	
	    double ny = enemy.getImageView().getLayoutY(); 
	    keepx = nx;
	    keepy = ny;
	    item.setItem(nx,ny);
	} 
    }
    
    /* 弾が敵に当たった時の分裂処理 */
    public void Split_Enemy(Enemy enemy){
	double nx = enemy.getImageView().getLayoutX();	
	double ny = enemy.getImageView().getLayoutY();
	printHitEffect(nx,ny);
	double nw = enemy.getWidth();
	double new_width;
	if(nw == 100.0){
	    new_width = 50.0;
	    addSplitEnemy(nx,ny,new_width);
	} else if(nw == 50.0){
	    new_width = 30.0;
	    addSplitEnemy(nx,ny,new_width);
	} else if(nw == 30.0){
	    new_width = 15.0;
	    addSplitEnemy(nx,ny,new_width);
	}
    }

    /* 弾が敵に当たったときのエフェクトの表示 */
    public void printHitEffect(double x,double y){
	putyun.setLayoutX(x);
	putyun.setLayoutY(y-10);
	putyun.setVisible(true);
	putyuntimer = 50;
    }
    
    /* 分裂した敵の表示処理 */
    public void addSplitEnemy(double x,double y,double width){
	Enemy enemy_left = new Enemy(x,y,width,Math.max(y-50.0,1.0),vx,-1.0);
	Enemy enemy_right = new Enemy(x,y,width,Math.max(y-50.0,1.0),vx,1.0);
	enemys[enemycnt] = enemy_left;
	enemys[enemycnt+1] = enemy_right;
	BubbleMonster.setCharaImage(enemys[enemycnt].getImageView());
	BubbleMonster.setCharaImage(enemys[enemycnt+1].getImageView());
	enemycnt += 2;
    }

    /* 弾のエフェクトのタイマー関数 */
    public void putyunTimerCount(){
	if(putyuntimer == -1){
	    return;
	} else if(putyuntimer == 0){
	    putyun.setVisible(false);
	}
	putyuntimer--;
	    
    }

    /* プレイヤー自身と敵の当たり判定① */
    public void isIntersects_Player_Enemy(){
	for(int i=0;i<enemycnt;i++){
	    if(enemydeadflg[i]){
		isIntersects_Player_Enemy(enemys[i]);
	    }
	}
    }
    
    /* プレイヤー自身と敵の当たり判定② */
    public void isIntersects_Player_Enemy(Enemy enemy){
	ImageView pi = chara.getImageView();
	Bounds playerBound = pi.getBoundsInParent();
	ImageView ei = enemy.getImageView();
	Bounds enemyBound = ei.getBoundsInParent();
	if(playerBound.intersects(enemyBound)){
	    int nl = playerlife.getNowLife() - 1;
	    playerLifeChange(nl);
	    BubbleMonster.setLifeImage(playerlife.getLifeArray(),nl,playerlife.getMaxLife());    
	    isLifeRemain(nl,7);

	    itemjudge2 =0;
            item.removeitem();
            reversejudge =0;
	}
    }


    /*アイテムとの当たり判定及び*/
    public void isIntersects_Player_Item(){
        ImageView pi = chara.getImageView();
        Bounds playerBound = pi.getBoundsInParent();
        ImageView ii = item.getImageView();
        Bounds itemBound = ii.getBoundsInParent();
        if(playerBound.intersects(itemBound) && itemgetjudge == 0){
	    itemjudge2 = 0;//再びアイテム出現可
	    itemgetjudge = 1;//アイテム取得不可
	    item.removeitem();
	    if( Item.getitemtype() == 0)   {//回復
		int nl = playerlife.getNowLife() + 1;
		playerLifeChange(nl);
		BubbleMonster.setLifeImage(playerlife.getLifeArray(),nl,playerlife.getMaxLife());
	    }
	    else if(Item.getitemtype() == 1){//スコア
		nowscore += 100;
		BubbleMonster.setScoreLabel(nowscore);
	    }
	    else if(Item.getitemtype() == 2){//時間
		timecount -= 500;
		BubbleMonster.itemTimeGauge();
		
	    }
	    else if(Item.getitemtype() == 3){//混乱
		reversejudge = 1;
	    }
            
	    /*
	      else if(Item.getitemtype() == 4){//強い銃
	      prickjudge = 1;
	      }*/
	}
    }//変更ここまで
}
