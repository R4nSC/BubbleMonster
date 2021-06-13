import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class BubbleMonster extends Application {
    public static Stage stage;
    private static GameController gamecontroller;
    private static Key key;

    /* ----- ゲーム全体の要素 -------------------- */
    private static AnchorPane pane;
    private static ImageView background;
    /* ----- ここまで　　　　 -------------------- */

    /* ----- タイトル画面　　 -------------------- */
    private static ImageView title;
    private static ImageView startbutton;
    /* ----- ここまで　　　　 -------------------- */
    
    /* ----- ゲーム画面の要素 -------------------- */
    private static AnchorPane gamefield;
    private static Label lb1;
    private static Label lb2;
    private static Label lb3;
    private static Label lb4;
    private static GridPane lifebox;
    private static Rectangle timeredgauge;
    private static Rectangle timewhitegauge;
    private static ImageView stateimage;
    /* ----- ここまで　　　　 -------------------- */
    
    @Override
    public void start(Stage primaryStage) throws Exception {
	stage = primaryStage;
	primaryStage.setTitle("Bubble Monster");

	/* 大元のウィンドウ(背景付き)の生成 */
	pane = new AnchorPane();
	background = new ImageView();
	Image wallImage = new Image("img/wall2.png");
	background.setImage(wallImage);
	pane.getChildren().add(background);

	/* ゲームが行われている場所 */
	gamefield = new AnchorPane();
	gamefield.setPrefSize(700,450);
	gamefield.setLayoutX(50.0);
	gamefield.setId("GameField");

	/* プレイヤーの名前のラベル */
	lb1 = new Label("Player");
	lb1.setPrefSize(150,35);
	lb1.setLayoutX(200);
	lb1.setLayoutY(482);
	lb1.setId("nameLabel");
	lb1.getStyleClass().add("label");

	/* プレイヤーの取ったスコアのラベル */
	lb2 = new Label("0");
	lb2.setPrefSize(150,35);
	lb2.setLayoutX(200);
	lb2.setLayoutY(527);
	lb2.setId("scoreLabel");
	lb2.getStyleClass().add("label");

	/* プレイヤーの名前のラベル */
	lb3 = new Label("Stage");
	lb3.setPrefSize(120,40);
	lb3.setLayoutX(40);
	lb3.setLayoutY(485);
	lb3.setId("stageLabel");
	lb3.getStyleClass().add("label");

	/* プレイヤーの取ったスコアのラベル */
	lb4 = new Label("1");
	lb4.setPrefSize(50,40);
	lb4.setLayoutX(75);
	lb4.setLayoutY(520);
	lb4.setId("stagenumLabel");
	lb4.getStyleClass().add("label");

	/* プレイヤーの体力ゲージ */
	lifebox = new GridPane();
	lifebox.setPrefSize(400,40);
	lifebox.setLayoutX(370);
	lifebox.setLayoutY(500);
	lifebox.setHgap(10);
	lifebox.setId("lifeBox");

	/* 制限時間のゲージ① */
	timeredgauge = new Rectangle();
	timeredgauge.setWidth(800);
	timeredgauge.setHeight(20);
	timeredgauge.setLayoutX(0);
	timeredgauge.setLayoutY(457);
	timeredgauge.setFill(Color.ORANGE);

	/* 制限時間のゲージ② */
	timewhitegauge = new Rectangle();
	timewhitegauge.setWidth(800);
	timewhitegauge.setHeight(20);
	timewhitegauge.setLayoutX(0);
	timewhitegauge.setLayoutY(457);
	timewhitegauge.setFill(Color.WHITE);

	/* タイトル画面に使用する画像2つ */
	title = new ImageView(new Image("img/title.png"));
	title.setLayoutX(183);
	title.setLayoutY(80);
	startbutton = new ImageView(new Image("img/start.png"));
	startbutton.setLayoutX(250);
	startbutton.setLayoutY(420);

	Canvas canvas = new Canvas(800,600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	pane.getChildren().add(canvas);

	Scene myScene = new Scene(pane,800,600);
	myScene.getStylesheets().add("GameStyle.css");
	primaryStage.setScene(myScene);

	/*　キーボードの操作を監視 */
	myScene.setOnKeyPressed( new EventHandler<KeyEvent>(){
		public void handle(KeyEvent e){
		    key.key_pressed(e);
		}
	    });
	
	myScene.setOnKeyReleased( new EventHandler<KeyEvent>(){
		public void handle(KeyEvent e){
		    key.key_released(e);
		}
	    });

	/* スタートボタンが押されたときに下の処理を行う */
	startbutton.setOnMouseClicked( new EventHandler<MouseEvent>(){
		@Override
		public void handle(MouseEvent e){
		    /* 画面のオブジェクトを変更 */
		    operateTitleObject(false);
		    operateGameObject(true);
		    
		    /* キー操作のクラスを生成 */
		    key = new Key();
		    
		    /* ゲームを実行 */
		    //Music.openingstop();
		    //Music.musicstart();
		    gamecontroller = new GameController(gc,key);
		    gamecontroller.start();
		}
	    });

	/* スタートボタンの上にカーソルをのせるとマウスカーソルの形を変える */
	startbutton.setOnMouseEntered( new EventHandler<MouseEvent>(){
		@Override
		public void handle(MouseEvent e){
		    pane.setCursor(Cursor.HAND);
		}
	    });

	/* スタートボタンの上から出たらマウスカーソルの形を元に戻す */
	startbutton.setOnMouseExited( new EventHandler<MouseEvent>(){
		@Override
		public void handle(MouseEvent e){
		    pane.setCursor(Cursor.DEFAULT);
		}
	    });
	
	/* ウィンドウにすべての要素を追加して表示 */
	pane.getChildren().addAll(title,startbutton);
	operateGameObject(false);
	pane.getChildren().addAll(lb1,lb2,lb3,lb4);
	pane.getChildren().addAll(timewhitegauge,timeredgauge,lifebox,gamefield);
	pane.setPrefSize(800,600);

	key = new Key();
	gamecontroller = new GameController(gc,key);
	
	primaryStage.show();
    }
    
    public static void main(String[] args) {
	//Music.openingstart();
        launch(args);
    }

    public static void ceildown(Player chara){
	double h = gamefield.getPrefHeight();
	gamefield.setPrefHeight(h-0.1);
	double y = gamefield.getLayoutY();
	gamefield.setLayoutY(y+0.1);
	chara.up_move();
    }

    public static void InitGameField(double w,double h,double x,double y){
	setGameFieldSize(w,h);
	gamefield.setLayoutY(y);
	gamefield.setLayoutX(x);
    }
    
    public static void setGameFieldSize(double w,double h){
	gamefield.setPrefSize(w,h);
    }
    
    public static void operateTitleObject(boolean flg){
	title.setVisible(flg);
	startbutton.setVisible(flg);
    }
    
    public static void operateGameObject(boolean flg){
    	lb1.setVisible(flg);
	lb2.setVisible(flg);
	lb3.setVisible(flg);
	lb4.setVisible(flg);
	timewhitegauge.setVisible(flg);
	timeredgauge.setVisible(flg);
	lifebox.setVisible(flg);
	gamefield.setVisible(flg);
    }

    public static double getGameFieldHeight(){
	return gamefield.getPrefHeight();
    }

    public static double getGameFieldWidth(){
	return gamefield.getPrefWidth();
    }

    public static double getGameFieldLayoutX(){
	return gamefield.getLayoutX();
    }
    
    public static void GameFieldClear(){
	gamefield.getChildren().clear();
    }

    public static void setCharaImage(ImageView image){
	gamefield.getChildren().add(image);
    }

    public static void setLifeImage(ImageView[] Life,int life,int maxlife){
	lifebox.getChildren().clear();
	for(int i=0;i<life;i++){
	    GridPane.setConstraints(Life[i],i,0);
	    lifebox.getChildren().add(Life[i]);
	}
	for(int i=life;i<maxlife;i++){
	    GridPane.setConstraints(Life[i],i,0);
	    lifebox.getChildren().add(Life[i]);
	}
    }

    public static void setBulletImage(ImageView bullet){
	gamefield.getChildren().add(bullet);
    }

    public static void setGameOverImage(ImageView image){
	gamefield.getChildren().add(image);
    }

    public static void setPutyunImage(ImageView image){
	gamefield.getChildren().add(image);
    }

    public static void changeTimeGauge(double downgauge){
	double nw = timeredgauge.getWidth();
	nw -= downgauge;
	timeredgauge.setWidth(Math.max(0,nw));
    }

    public static void itemTimeGauge(){
	double nw = timeredgauge.getWidth();
	nw += 250;
	timeredgauge.setWidth(Math.max(0,nw));
    }

    public static void resetTimeGauge(){
	timeredgauge.setWidth(800.0);
    }

    public static void ChangeStateImage(Image image,double x,double y){
	stateimage = new ImageView();
	stateimage.setLayoutX(x);
	stateimage.setLayoutY(y);
	stateimage.setImage(image);
	gamefield.getChildren().add(stateimage);
	printStateImage();
    }

    public static void printStateImage(){	
	stateimage.setVisible(true);
    }
    
    public static void removeStateImage(){
	stateimage.setVisible(false);
	//stateimage = null;
	removeImageView(stateimage);
    }

    public static void setScoreLabel(int num){
	lb2.setText(String.valueOf(num));
    }
    
    public static void setStageLabel(int num){
	lb4.setText(String.valueOf(num));
    }

    public static void printItem(ImageView item){
	gamefield.getChildren().add(item);
    }

    public static void removeImageView(ImageView iv){
	gamefield.getChildren().remove(iv);
    }
}
