import javafx.scene.input.*;

/* キー操作のクラス */
public class Key{
    private boolean left,right,space;
    private int left_count,right_count,space_count;
    private KeyCode key_code;

    /* コンストラクタ */
    Key(){
	left = false;
	right = false;
	space = false;
	left_count = 0;
	right_count = 0;
	space_count = 0;
	key_code = null;
    }

    /* キーボードが押されたとき */
    void key_pressed(KeyEvent e){
	switch(e.getCode()){
	case LEFT:
	    left = true;
	    break;
	case RIGHT:
	    right = true;
	    break;
	case SPACE:
	    space = true;
	    break;
	default:
	    break;
	}
	key_code = e.getCode();
    }

    /* キーボードが押されている状況から離れた場合 */
    void key_released(KeyEvent e){
	switch(e.getCode()){
	case LEFT:
	    left = false;
	    break;
	case RIGHT:
	    right = false;
	    break;
	case SPACE:
	    space = false;
	    break;
	default:
	    break;
	}
	key_code = null;
    }

    /* キーボードがどのぐらいの長さ押されているか求める  */
    void calc_key_count(){
	if(left) left_count++;
	else left_count = 0;

	if(right) right_count++;
	else right_count = 0;

	if(space) space_count++;
	else space_count = 0;
    }

    /* 左の方向キーが押された回数を返す */
    int get_left(){
	return left_count;
    }

    /* 右の方向キーが押された回数を返す */
    int get_right(){
	return right_count;
    }

    /* スペースキーが押された回数を返す */
    int get_space(){
	return space_count;
    }
    
    /* 押されているキーボードのコードを返す */
    KeyCode get_key_code(){
	return key_code;
    }
}
