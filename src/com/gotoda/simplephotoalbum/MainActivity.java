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
	// Database�̐錾
	SQLiteDatabase photoDB;
	// GridView�̐錾
	GridView gridViewPhoto;
	// �A�_�v�^�[�̐錾
	ArrayAdapter<String> adapter;

    ////////////////////////////////////////////////////////    														
    /**
     * onCreate
     * @param con�@�R�l�N�V�����ł�
     * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
     * @return �Ȃ�
     * @throws SQLException SQL���s���ɋN���肤���O
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // �f�[�^�x�[�X�w���p�[�̐���
        PhotoDBHelper photoDBHelper
        	= new PhotoDBHelper(this, "photo_db", null, 1);
        // �f�[�^�x�[�X�̐���
        photoDB = photoDBHelper.getWritableDatabase();
           
        // ���C�A�E�g���GridViewPhoto��ϐ�gridViewPhoto�ɑ��
        gridViewPhoto =
        	(GridView)findViewById(R.id.GridViewPhoto);
        // �A�_�v�^�[�̍쐬
        adapter = new ArrayAdapter(this, R.layout.activity_grid);
        // �摜�̈ꗗ�\��
        showPhotos();
    }

    ////////////////////////////////////////////////////////    														
    /**
     * onCreateOptionsMenu
     * @param con�@�R�l�N�V�����ł�
     * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
     * @return �Ȃ�
     * @throws SQLException SQL���s���ɋN���肤���O
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    ////////////////////////////////////////////////////////    														
    /**
     * moveAddActivity
     * @param con�@�R�l�N�V�����ł�
     * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
     * @return �Ȃ�
     * @throws SQLException SQL���s���ɋN���肤���O
     */
    public void moveAddActivity(View view){
    	
    	// �o�^��ʂ̃C���e���g�̐���
    	Intent intent = new Intent(this, SimplePhotoAlbumAdd.class);
    	// �o�^��ʂ̌Ăяo��
    	startActivityForResult(intent, 1);
    }

    ////////////////////////////////////////////////////////    														
    /**
     * onActivityResult
     * @param con�@�R�l�N�V�����ł�
     * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
     * @return �Ȃ�
     * @throws SQLException SQL���s���ɋN���肤���O
     */	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onActivityResult(requestCode, resultCode, data);
		
		// �o�^��ʁi�P�j����߂��Ă����Ƃ��̏���
		if( requestCode == 1 ){
			// ���ʃR�[�h��Activity.RESULT_OK�ł���Βl�����o��
			if( resultCode == Activity.RESULT_OK ){
				// �p�����[�^����l�����o��
				String file = data.getStringExtra("file");
				String title = data.getStringExtra("title");
				float rating = data.getFloatExtra("rating", 0);
				
				// SQL���̍쐬
				String sql = "insert into photo_table"
					+ " (photo_file,photo_title,photo_rating)"
					+ " values('" + file + "','" + title + "',"
					+ rating + ")";
				// �f�[�^�x�[�X�փf�[�^��}��
				photoDB.execSQL(sql);
				
				// ���O�ɏo��
				Log.d("[SimplePhotoAlbum File]", file);
				Log.d("[SimplePhotoAlbum Title]", title);
				Log.d("[SimplePhptoAlbum Rating]", String.valueOf(rating));				
			}
		}
		
		// �摜�̈ꗗ�\��
		showPhotos();		
	}
    
     ////////////////////////////////////////////////////////    														
     /**
      * showPhotos
      * @param con�@�R�l�N�V�����ł�
      * @param tradebean�@���̓t�H�[���ɓ��͂��ꂽ�����������Ă��܂�
      * @return �Ȃ�
      * @throws SQLException SQL���s���ɋN���肤���O
      */
    private void showPhotos() {
		// �A�_�v�^�[�̃N���A
		adapter.clear();
		// �J�[�\���̐錾
		Cursor cursor = null;
		
		try {
			// SQL���̍쐬
			String sql
				= "select photo_id, photo_file, photo_title, photo_rating"
					+ " from photo_table"
					+ " order by photo_rating";
		
			// SQL�������s���A���ʂ��J�[�\���ɑ��
			cursor = photoDB.rawQuery(sql,null);
		
			// �J�[�\����擪�Ɉړ�
			cursor.moveToFirst();
		
			// �P�����f�[�^�����o��
			for (int i=0; i<cursor.getCount(); i++) {
				// �P�����̃f�[�^�����o��
				String photoData = cursor.getInt(0) + "\n"
						+ cursor.getString(1)   + "\n"
						+ cursor.getString(2)   + "\n"
						+ cursor.getFloat(3);
		
				// �₢���킹���ʂ��A�_�v�^�[�ɒǉ�
				adapter.add(photoData);
		
				// �J�[�\�������ֈړ�
				cursor.moveToNext();
			}
		} catch(Exception e) {
			// �G���[���b�Z�[�W�����O�ɏo�́@
			Log.e("SimplePhotoAlbum Error",e.toString());
		} finally {
			// �J�[�\�������@
			if (cursor != null) cursor.close();
		}
		
		// �A�_�v�^�[���Z�b�g�@
		gridViewPhoto.setAdapter(adapter);

      }
    
    
    
}
