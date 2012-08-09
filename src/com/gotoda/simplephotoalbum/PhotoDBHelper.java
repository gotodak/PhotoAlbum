package com.gotoda.simplephotoalbum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class PhotoDBHelper extends SQLiteOpenHelper {

    ////////////////////////////////////////////////////////    														
    /**
     * PhotoDBHelper
     * @param con�@�R�l�N�V�����ł�
     * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
     * @return �Ȃ�
     * @throws SQLException SQL���s���ɋN���肤���O
     */
	public PhotoDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

    ////////////////////////////////////////////////////////    														
    /**
     * onCreate
     * @param con�@�R�l�N�V�����ł�
     * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
     * @return �Ȃ�
     * @throws SQLException SQL���s���ɋN���肤���O
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
		// �e�[�u��phote_table�̒�`
		String sql = "create table photo_table"
				+ "(photo_id integer primary key autoincrement,"
				+ " photo_file text,"
				+ " photo_title text,"
				+ " photo_rating floate)";
		
		// �e�[�u���̍쐬
		db.execSQL(sql);

	}

    ////////////////////////////////////////////////////////    														
    /**
     * onUpgrade
     * @param con�@�R�l�N�V�����ł�
     * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
     * @return �Ȃ�
     * @throws SQLException SQL���s���ɋN���肤���O
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
