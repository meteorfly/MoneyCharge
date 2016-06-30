package com.cwp.cmoneycharge;

import java.util.List;
  
import com.cwp.cmoneycharge.R;

import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.Tb_income;
import cwp.moneycharge.dao.DBOpenHelper;
import cwp.moneycharge.dao.IncomeDAO;
import cwp.moneycharge.dao.ItypeDAO;

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
import android.widget.Toast;
public class Income extends Activity {
	int userid;
	Button baddincome; 
	ItypeDAO itypeDAO;
	
	public Income() {
		// TODO Auto-generated constructor stub
	}
	 
	ListView lvinfo;// ����ListView����
	String strType = "";// �����ַ�������¼��������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income);// ���ò����ļ�  

		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		lvinfo = (ListView) findViewById(R.id.lvinaccountinfo);// ��ȡ�����ļ��е�ListView���
		baddincome=(Button)findViewById(R.id.baddincome);//��Ӱ�ť
		itypeDAO=new ItypeDAO(Income.this); 
		 		
	}
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();// ʵ�ֻ����еķ���//  �����Զ��巽����ʾ������Ϣ
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		ShowInfo(R.id.btnininfo);
		lvinfo.setOnItemClickListener(new OnItemClickListener()// ΪListView�������¼�
		{
			// ��дonItemClick����
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// ��¼������Ϣ
				//Toast.makeText(Income.this,"strInfo:"+strInfo,Toast.LENGTH_LONG).show();
				String strno = strInfo.substring(0, strInfo.indexOf('|')).trim();// ��������Ϣ�н�ȡ������
				Intent intent = new Intent(Income.this, ModifyInP.class);// ����Intent����
				intent.putExtra("cwp.id", userid);
				intent.putExtra("cwp.message", new String[] { strno, strType });// ���ô�������  
				startActivity(intent);// ִ��Intent����
			}
		});
		baddincome.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(Income.this, AddIncome.class);// ����Intent����
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
	}
	private void ShowInfo(int intType) {// �������ݴ���Ĺ������ͣ���ʾ��Ӧ����Ϣ
		String[] strInfos = null;// �����ַ������飬�����洢������Ϣ
		ArrayAdapter<String> arrayAdapter = null;// ����ArrayAdapter����
		strType = "btnininfo";// ΪstrType������ֵ
		IncomeDAO incomedao = new IncomeDAO(Income.this);// ����IncomeDAO����
		// ��ȡ����������Ϣ�����洢��List���ͼ�����
		List<Tb_income> listinfos = incomedao.getScrollData(userid,0,
				(int) incomedao.getCount(userid));
		strInfos = new String[listinfos.size()];// �����ַ�������ĳ���
		int m = 0;// ����һ����ʼ��ʶ
		for (Tb_income tb_income : listinfos) {// ����List���ͼ���
			// �����������Ϣ��ϳ�һ���ַ������洢���ַ����������Ӧλ�� 
			strInfos[m] =tb_income.getNo()+ " |  " +itypeDAO.getOneName(userid, tb_income.getType())
					+ "   " + String.valueOf(tb_income.getMoney()) + "Ԫ           "
					+ tb_income.getTime();
			m++;// ��ʶ��1
		}
		// ʹ���ַ��������ʼ��ArrayAdapter����
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);// ΪListView�б���������Դ
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //���/����/���η��ؼ�
	    	Intent intent=new Intent(Income.this,Index.class);
			intent.putExtra("cwp.id",userid);
			startActivity(intent);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
