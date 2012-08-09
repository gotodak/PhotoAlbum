package com.gotoda.simplephotoalbum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class PhotoDBHelper extends SQLiteOpenHelper {

    ////////////////////////////////////////////////////////    														
    /**
     * PhotoDBHelper
     * @param con　コネクションです
     * @param tradebean　入力フォームに入力された文字が入っています
     * @return なし
     * @throws SQLException SQL実行時に起きりうる例外
     */
	public PhotoDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    ////////////////////////////////////////////////////////    														
    /**
     * onCreate
     * @param con　コネクションです
     * @param tradebean　入力フォームに入力された文字が入っています
     * @return なし
     * @throws SQLException SQL実行時に起きりうる例外
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自動生成されたメソッド・スタブ
		
		// テーブルphote_tableの定義
		String sql = "create table photo_table"
				+ "(photo_id integer primary key autoincrement,"
				+ " photo_file text,"
				+ " photo_title text,"
				+ " photo_rating floate)";
		
		// テーブルの作成
		db.execSQL(sql);

	}

    ////////////////////////////////////////////////////////    														
    /**
     * onUpgrade
     * @param con　コネクションです
     * @param tradebean　入力フォームに入力された文字が入っています
     * @return なし
     * @throws SQLException SQL実行時に起きりうる例外
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
