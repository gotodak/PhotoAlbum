package com.gotoda.simplephotoalbum;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

public class SimplePhotoAlbumAdd extends Activity {
	
	// 部品の宣言
	// Comment
	ImageView imageViewAdd;
	Spinner spinnerAdd;
	EditText editTextAdd;
	RatingBar ratingBarAdd;
	
	

    ////////////////////////////////////////////////////////    														
    /**
     * onCreate
     * @param con　コネクションです
     * @param tradebean　入力フォームに入力された文字が入っています
     * @return なし
     * @throws SQLException SQL実行時に起きりうる例外22
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		// レイアウトのセット
		setContentView(R.layout.activity_add);
		
		// レイアウト上の部品を変数に代入
		imageViewAdd 
			= (ImageView)findViewById(R.id.ImageViewAdd);
		spinnerAdd = (Spinner)findViewById(R.id.SpinnerAdd);
		editTextAdd = (EditText)findViewById(R.id.EditTextAdd);
		ratingBarAdd = (RatingBar)findViewById(R.id.RatingBarAdd);
		
		// SDカードの画像ファイル群をスピナーにセット
		// adapterの作成
		ArrayAdapter<String> adapter
			= new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item);
		// SDカードのディレクトリを取得
		File directory
			= Environment.getExternalStorageDirectory();
		// SDカードのディレクトリからすべてのファイルを取得
		File[] files = directory.listFiles();
		// adapterにファイル名を追加
		for ( int i=0; i<files.length; i++ ){
			// JPEGファイルのみを追加
			if( files[i].getName().endsWith(".jpg")){
				adapter.add(files[i].getPath());
			}
		}
		// spinnerAddにadapterをセット
		spinnerAdd.setAdapter(adapter);
		
		// spinnerFileから選択した要素を取り出す
		spinnerAdd.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener(){

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO 自動生成されたメソッド・スタブ
						// 選択した要素の位置をログに出力
						Log.d("[Spinner]", "Position; "+ arg2);
						
						// 選択されたファイル
						String selectedFile
							= (String)arg0.getItemAtPosition(arg2);
						// 選択されたファイルからビットマップを生成
						Bitmap bitmap
							= BitmapFactory.decodeFile(selectedFile);
						// イメージビューにビットマップをセット
						imageViewAdd.setImageBitmap(bitmap);
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO 自動生成されたメソッド・スタブ
						
					}
					
				}
		);
	}
	
	
    ////////////////////////////////////////////////////////    														
    /**
     * closeActivity
     * @param con　コネクションです
     * @param tradebean　入力フォームに入力された文字が入っています
     * @return なし
     * @throws SQLException SQL実行時に起きりうる例外
     */
	public void closeActivity(View view){
		// Setボタンがクリックされたときに値を送信
		if( view.getId() == R.id.ButtonSet){
			// インテントのパラメータに値をセットする
			Intent intent = new Intent();
			intent.putExtra("file", spinnerAdd.getSelectedItem().toString());
			intent.putExtra("title", editTextAdd.getText().toString());
			intent.putExtra("rating", ratingBarAdd.getRating());
			
			// メイン画面にパラメータを送信する
			setResult(Activity.RESULT_OK, intent);
			
		}
		
		
		// 登録画面を閉じる
		finish();
	}
	
	

}
