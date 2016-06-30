package com.cwp.cmoneycharge;

import java.util.List;

import cwp.moneycharge.dao.NoteDAO;
import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.Tb_note;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button; 
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Note extends Activity {

	int userid;
	Button baddnote; 
	NoteDAO noteDAO;
	ListView lvinfo;// ����ListView����
	
	public Note() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);// ���ò����ļ�  
		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		lvinfo = (ListView) findViewById(R.id.lvinnoteinfo);// ��ȡ�����ļ��е�ListView���
		baddnote=(Button)findViewById(R.id.baddnote);//��Ӱ�ť
		noteDAO=new NoteDAO(Note.this);//  �����Զ��巽����ʾ������Ϣ
	}
	@Override
	protected void onStart(){
		super.onStart();
		
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		ShowInfo();
		lvinfo.setOnItemClickListener(new OnItemClickListener()// ΪListView�������¼�
		{
			// ��дonItemClick����
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// ��¼������Ϣ
				String strno = strInfo.substring(0, strInfo.indexOf('|')).trim();// ��������Ϣ�н�ȡ������
				Intent intent = new Intent(Note.this, ModifyNote.class);// ����Intent����
				intent.putExtra("cwp.id", userid);
				intent.putExtra("strno", Integer.parseInt(strno));
				startActivity(intent);// ִ��Intent����
			}
		});
		baddnote.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(Note.this, AddNote.class);// ����Intent����
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
		
	}
	private void ShowInfo( ) {// �������ݴ���Ĺ������ͣ���ʾ��Ӧ����Ϣ
		String[] strInfos = null;// �����ַ������飬�����洢������Ϣ
		ArrayAdapter<String> arrayAdapter = null;// ����ArrayAdapter���� 
		NoteDAO Notedao = new NoteDAO(Note.this);// ����NoteDAO����
		// ��ȡ����������Ϣ�����洢��List���ͼ�����
		List<Tb_note> listinfos = Notedao.getScrollData(userid,0,
				(int) Notedao.getCount(userid));
		strInfos = new String[listinfos.size()];// �����ַ�������ĳ���
		int m = 0;// ����һ����ʼ��ʶ
		for (Tb_note tb_note : listinfos) {// ����List���ͼ���
			// �����������Ϣ��ϳ�һ���ַ������洢���ַ����������Ӧλ�� 
			strInfos[m] =tb_note.getNo()+ " |  "   +tb_note.getNote();
			m++;// ��ʶ��1
		}
		// ʹ���ַ��������ʼ��ArrayAdapter����
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);// ΪListView�б���������Դ
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //���/����/���η��ؼ�
	    	Intent intent=new Intent(Note.this,Index.class);
			intent.putExtra("cwp.id",userid);
			startActivity(intent);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
