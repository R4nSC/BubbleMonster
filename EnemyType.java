import java.util.HashMap;

public class EnemyType {
    private double[] hgap;
    private double[] wgap;
    private double[] jump_power;
    private int[] score;
    
    private HashMap<Double,Integer> Bond_num_width;
    
    EnemyType(){
	/* マップで配列の番号と横の長さを結びつける */
	Bond_num_width = new HashMap<Double,Integer>();
	Bond_num_width.put(100.0,0);
	Bond_num_width.put(50.0,1);
	Bond_num_width.put(30.0,2);
	Bond_num_width.put(15.0,3);

	/* 横の壁の当たり判定の調整 */
	hgap = new double[4];
	hgap[0] = -50.0;
	hgap[1] = 0.0;
	hgap[2] = 15.0;
	hgap[3] = 35.0;

	/* 床の当たり判定の調整 */
	wgap = new double[4];
	wgap[0] = -50.0;
	wgap[1] = 0.0;
	wgap[2] = 15.0;
	wgap[3] = 33.0;

	/* ジャンプする高さの設定 */
	jump_power = new double[4];
	jump_power[0] = 100.0;
	jump_power[1] = 200.0;
	jump_power[2] = 270.0;
	jump_power[3] = 320.0;

	/* 敵を倒したときのスコアの設定 */
	score = new int[4];
	score[0] = 10;
	score[1] = 30;
	score[2] = 50;
	score[3] = 100;
    }

    /* 右の壁との判定のギャップを返す */
    public double getHgap(double w){
	return hgap[Bond_num_width.get(w)];
    }

    /* 下の床との判定のギャップを返す */
    public double getWgap(double w){
	return wgap[Bond_num_width.get(w)];
    }

    /* ジャンプする高さを返す */
    public double getJumpPower(double w){
	return jump_power[Bond_num_width.get(w)];
    }

    /* 倒したときの得られるスコアを返す */
    public int getEnemyScore(double w){
	return score[Bond_num_width.get(w)]; 
    }
}
