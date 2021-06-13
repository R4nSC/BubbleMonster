import java.util.HashMap;

/* ステージクラス(ステージの情報をいろいろ格納) */
public class StagesInfo {
    private HashMap<Integer,Enemy[]> enemycollection;
    private int[] first_enemycnt;

    /* コンストラクタ */
    StagesInfo(){
	enemycollection = new HashMap<Integer,Enemy[]>();
	first_enemycnt = new int[11];
	
	Enemy[] stage1 = new Enemy[1];
	stage1[0] = new Enemy(100.0,320.0,15.0,320.0,2.0,1.0);
	first_enemycnt[1] = 1;
	enemycollection.put(1,stage1);
	
	Enemy[] stage2 = new Enemy[3];
	stage2[0] = new Enemy(500.0,270.0,30.0,270.0,2.0,-1.0);
	first_enemycnt[2] = 1;
	enemycollection.put(2,stage2);

	Enemy[] stage3 = new Enemy[6];
	stage3[0] = new Enemy(100.0,270.0,30.0,270.0,2.0,1.0);
	stage3[1] = new Enemy(500.0,270.0,30.0,270.0,2.0,-1.0);
	first_enemycnt[3] = 2;
	enemycollection.put(3,stage3);
	
	Enemy[] stage4 = new Enemy[15];
	stage4[0] = new Enemy(100.0,100.0,100.0,100.0,2.0,1.0);
	first_enemycnt[4] = 1;
	enemycollection.put(4,stage4);

	Enemy[] stage5 = new Enemy[14];
	stage5[0] = new Enemy(100.0,200.0,50.0,200.0,2.0,-1.0);
	stage5[1] = new Enemy(500.0,200.0,50.0,200.0,2.0,1.0);
	first_enemycnt[5] = 2;
	enemycollection.put(5,stage5);

	Enemy[] stage6 = new Enemy[6];
	stage6[0] = new Enemy(10.0,320.0,15.0,320.0,2.0,-1.0);
	stage6[1] = new Enemy(90.0,320.0,15.0,320.0,2.0,-1.0);
	stage6[2] = new Enemy(170.0,320.0,15.0,320.0,2.0,-1.0);
	stage6[3] = new Enemy(520.0,320.0,15.0,320.0,2.0,1.0);
	stage6[4] = new Enemy(600.0,320.0,15.0,320.0,2.0,1.0);
	stage6[5] = new Enemy(680.0,320.0,15.0,320.0,2.0,1.0);
	first_enemycnt[6] = 6;
	enemycollection.put(6,stage6);
	
	Enemy[] stage7 = new Enemy[10];
	stage7[0] = new Enemy(10.0,100.0,15.0,100.0,2.0,1.0);
	stage7[1] = new Enemy(40.0,100.0,15.0,100.0,2.0,1.0);
	stage7[2] = new Enemy(70.0,100.0,15.0,100.0,2.0,1.0);
	stage7[3] = new Enemy(160.0,100.0,15.0,100.0,2.0,1.0);
	stage7[4] = new Enemy(190.0,100.0,15.0,100.0,2.0,1.0);
	stage7[5] = new Enemy(500.0,100.0,15.0,100.0,2.0,-1.0);
	stage7[6] = new Enemy(530.0,100.0,15.0,100.0,2.0,-1.0);
	stage7[7] = new Enemy(620.0,100.0,15.0,100.0,2.0,-1.0);
	stage7[8] = new Enemy(650.0,100.0,15.0,100.0,2.0,-1.0);
	stage7[9] = new Enemy(680.0,100.0,15.0,100.0,2.0,-1.0);
	first_enemycnt[7] = 10;
	enemycollection.put(7,stage7);

	Enemy[] stage8 = new Enemy[14];
	stage8[0] = new Enemy(100.0,200.0,50.0,200.0,2.0,-1.0);
	stage8[1] = new Enemy(500.0,200.0,50.0,200.0,2.0,1.0);
	first_enemycnt[8] = 2;
	enemycollection.put(8,stage8);
	
	Enemy[] stage9 = new Enemy[14];
	stage9[0] = new Enemy(100.0,200.0,50.0,200.0,2.0,0);
	stage9[1] = new Enemy(500.0,200.0,50.0,200.0,2.0,0);
	first_enemycnt[9] = 2;
	enemycollection.put(9,stage9);

	Enemy[] stage10 = new Enemy[21];
	stage10[0] = new Enemy(100.0,200.0,50.0,200.0,5.0,-1.0);
	stage10[1] = new Enemy(300.0,200.0,50.0,200.0,5.0,0);
	stage10[2] = new Enemy(500.0,200.0,50.0,200.0,5.0,1.0);
	first_enemycnt[10] = 3;
	enemycollection.put(10,stage10);

	
	/*
	  天井が下がる ○
	  縦幅がちっちゃい固定 ○
	  横狭くなる ○
	  壁 ☓
	  モンスター位置固定 ○
	  x方向めっちゃ飛ぶ ○
	*/
    }

    /* ステージの情報を入手 */
    public Enemy[] getStageInfo(int num){
	return enemycollection.get(num);
    }

    /* ステージの最初の時点での敵の数を返す */
    public int getFirstEnemycnt(int num){
	return first_enemycnt[num];
    }
}

