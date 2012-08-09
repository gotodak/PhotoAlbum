package com.gotoda.simplephotoalbum;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends Activity {
	// Databaseの宣言
	SQLiteDatabase photoDB;
	// GridViewの宣言
	GridView gridViewPhoto;
	// アダプターの宣言
	ArrayAdapter<String> adapter;

    ////////////////////////////////////////////////////////    														
    /**
     * onCreate
     * @param con　コネクションです
     * @param tradebean　入力フォームに入力された文字が入っています
     * @return なし
     * @throws SQLException SQL実行時に起きりうる例外
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // データベースヘルパーの生成
        PhotoDBHelper photoDBHelper
        	= new PhotoDBHelper(this, "photo_db", null, 1);
        // データベースの生成
        photoDB = photoDBHelper.getWritableDatabase();
           
        // レイアウト上のGridViewPhotoを変数gridViewPhotoに代入
        gridViewPhoto =
        	(GridView)findViewById(R.id.GridViewPhoto);
        // アダプターの作成
        adapter = new ArrayAdapter(this, R.layout.activity_grid);
        // 画像の一覧表示
        showPhotos();
    }

    ////////////////////////////////////////////////////////    														
    /**
     * onCreateOptionsMenu
     * @param con　コネクションです
     * @param tradebean　入力フォームに入力された文字が入っています
     * @return なし
     * @throws SQLException SQL実行時に起きりうる例外
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    ////////////////////////////////////////////////////////    														
    /**
     * moveAddActivity
     * @param con　コネクションです
     * @param tradebean　入力フォームに入力された文字が入っています
     * @return なし
     * @throws SQLException SQL実行時に起きりうる例外
     */
    public void moveAddActivity(View view){
    	
    	// 登録画面のインテントの生成
    	Intent intent = new Intent(this, SimplePhotoAlbumAdd.class);
    	// 登録画面の呼び出し
    	startActivityForResult(intent, 1);
    }

    ////////////////////////////////////////////////////////    														
    /**
     * onActivityResult
     * @param con　コネクションです
     * @param tradebean　入力フォームに入力された文字が入っています
     * @return なし
     * @throws SQLException SQL実行時に起きりうる例外
     */	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityResult(requestCode, resultCode, data);
		
		// 登録画面（１）から戻ってきたときの処理
		if( requestCode == 1 ){
			// 結果コードがActivity.RESULT_OKであれば値を取り出す
			if( resultCode == Activity.RESULT_OK ){
				// パラメータから値を取り出す
				String file = data.getStringExtra("file");
				String title = data.getStringExtra("title");
				float rating = data.getFloatExtra("rating", 0);
				
				// SQL文の作成
				String sql = "insert into photo_table"
					+ " (photo_file,photo_title,photo_rating)"
					+ " values('" + file + "','" + title + "',"
					+ rating + ")";
				// データベースへデータを挿入
				photoDB.execSQL(sql);
				
				// ログに出力
				Log.d("[SimplePhotoAlbum File]", file);
				Log.d("[SimplePhotoAlbum Title]", title);
				Log.d("[SimplePhptoAlbum Rating]", String.valueOf(rating));				
			}
		}
		
		// 画像の一覧表示
		showPhotos();		
	}
    
     ////////////////////////////////////////////////////////    														
     /**
      * showPhotos
      * @param con　コネクションです
      * @param tradebean　入力フォームに入力された文字が入っています
      * @return なし
      * @throws SQLException SQL実行時に起きりうる例外
      */
    private void showPhotos() {
		// アダプターのクリア
		adapter.clear();
		// カーソルの宣言
		Cursor cursor = null;
		
		try {
			// SQL文の作成
			String sql
				= "select photo_id, photo_file, photo_title, photo_rating"
					+ " from photo_table"
					+ " order by photo_rating";
		
			// SQL文を実行し、結果をカーソルに代入
			cursor = photoDB.rawQuery(sql,null);
		
			// カーソルを先頭に移動
			cursor.moveToFirst();
		
			// １件ずつデータを取り出す
			for (int i=0; i<cursor.getCount(); i++) {
				// １件分のデータを取り出す
				String photoData = cursor.getInt(0) + "\n"
						+ cursor.getString(1)   + "\n"
						+ cursor.getString(2)   + "\n"
						+ cursor.getFloat(3);
		
				// 問い合わせ結果をアダプターに追加
				adapter.add(photoData);
		
				// カーソルを次へ移動
				cursor.moveToNext();
			}
		} catch(Exception e) {
			// エラーメッセージをログに出力　
			Log.e("SimplePhotoAlbum Error",e.toString());
		} finally {
			// カーソルを閉じる　
			if (cursor != null) cursor.close();
		}
		
		// アダプターをセット　
		gridViewPhoto.setAdapter(adapter);

      }
    
    
    
}
