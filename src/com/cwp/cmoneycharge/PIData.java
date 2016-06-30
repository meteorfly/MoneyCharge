package com.cwp.cmoneycharge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import cwp.moneycharge.dao.IncomeDAO;
import cwp.moneycharge.dao.PayDAO;
import cwp.moneycharge.model.ActivityManager;
import cwp.moneycharge.model.Datapicker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class PIData  extends Activity{
	int userid;
	Intent intentr;
	PayDAO payDAO;
	IncomeDAO incomeDAO;
	Time time ;    
	int defaultMonth;
	int defaultYear; 
	LinearLayout chart;
	Button beforet,aftert,anytime;
	Spinner year,month,day,yeare,monthe,daye;//�����ϵ�����ʱ�� 
    List<String> yearlist;
	private GraphicalView lChart;
	Adapter adapter;
	XYSeriesRenderer xyRenderer;
	XYSeries seriesp,seriesi;
	List<Datapicker> datapickerp, datapickeri;//���룬֧�����ݼ�
	String date1,date2;//��ȡ�û�ѡ�����������ʱ��
    //1 ������ʾ��Ⱦͼ
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    //2,������ʾ
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    //2.1��������
    Random r = new Random();

	public PIData() {
		// TODO Auto-generated constructor stub
	}
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.pidata);
			ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
	        time = new Time("GMT+8");    
	        time.setToNow();   
	        defaultMonth=time.month+1;//����Ĭ���·�
	        defaultYear=time.year;
	        beforet=(Button)findViewById(R.id.before);
	        aftert=(Button)findViewById(R.id.after);
	        anytime=(Button)findViewById(R.id.anytime);
	        year=(Spinner)findViewById(R.id.year);
	        month=(Spinner)findViewById(R.id.month);
	        day=(Spinner)findViewById(R.id.day);
	        yeare=(Spinner)findViewById(R.id.yeare);
	        monthe=(Spinner)findViewById(R.id.monthe);
	        daye=(Spinner)findViewById(R.id.daye);
	        chart=(LinearLayout)findViewById(R.id.chart);
	        
	        yearlist=new ArrayList<String>(); //��������б� spinner
	        
	        //������
	        for(int i=0;i<=10;i++){
	        	yearlist.add(String.valueOf(defaultYear-i));
	        } 
	        adapter=new ArrayAdapter<String>(PIData.this,android.R.layout.simple_spinner_item,yearlist);
	        year.setAdapter((SpinnerAdapter) adapter);
	        yeare.setAdapter((SpinnerAdapter) adapter);
	
	 }
	 @Override
	 protected void onStart(){
		 	super.onStart();
		 	intentr=getIntent();
		 	userid=intentr.getIntExtra("cwp.id",100000001); 
		 	defaultMonth=intentr.getIntExtra("default", defaultMonth);  
		 	defaultYear=intentr.getIntExtra("defaulty", defaultYear);  
		 	int type=intentr.getIntExtra("type",0);//Ϊ0��ѡ�������£�Ϊ1��ѡ������ʱ��
	        payDAO=new PayDAO(PIData.this);
	        incomeDAO=new IncomeDAO(PIData.this);
	        
	        //3. �Ե�Ļ��ƽ�������
	        xyRenderer = new XYSeriesRenderer();
	        //3.1������ɫ
	        xyRenderer.setColor(Color.BLUE);
	        //3.2���õ����ʽ
	        xyRenderer.setPointStyle(PointStyle.SQUARE);
	        xyRenderer.setLineWidth(10);
	         xyRenderer.setPointStyle(PointStyle.CIRCLE);
	         xyRenderer.setFillPoints(true);
	        renderer.addSeriesRenderer(xyRenderer); 
	         xyRenderer = new XYSeriesRenderer();
	         xyRenderer.setColor(Color.RED);
	         xyRenderer.setPointStyle(PointStyle.SQUARE);
	         xyRenderer.setLineWidth(10);
	         xyRenderer.setPointStyle(PointStyle.CIRCLE);
	         xyRenderer.setFillPoints(true);
	         renderer.addSeriesRenderer(xyRenderer);
	         
	         renderer.setShowGrid(true);
	         renderer.setGridColor(Color.GRAY);
	         renderer.setAxisTitleTextSize(44);// ��������������ı���С
	         renderer.setChartTitleTextSize(54); // ����ͼ������ı���С
	         renderer.setLabelsTextSize(34); // �������ǩ�ı���С
	         renderer.setLegendTextSize(54);
	         renderer.setPointSize(10); 
		     renderer.setXTitle( defaultYear+"��  "+defaultMonth+"�� "+"����" );//����X������
		     renderer.setYTitle( "���" );
		     renderer.setMargins(new int[] { 50, 120, 100,70 }); 
		   
		     renderer.setXLabels(15);
		     renderer.setYLabels(10);
		     renderer.setXAxisMin(1);
		     renderer.setYAxisMin(10);
		    
		     renderer.setXLabelsAlign(Align.RIGHT);
		     renderer.setYLabelsAlign(Align.RIGHT);  
		     renderer.setRange(new double[]{0d, 30d, 0d, 300d}); //����chart����ͼ��Χ
	        
		     if(type==0){
	        
		        datapickerp=payDAO.getDataMonth(userid, defaultYear, defaultMonth) ;
			 	datapickeri=incomeDAO.getDataMonth(userid, defaultYear,defaultMonth);  
		 	
			 	
		     }else{
		    	 Log.i("========",date1+"  222  "+date2);
				 date1=intentr.getStringExtra("date1");
			     date2=intentr.getStringExtra("date2");
		    	 datapickerp=payDAO.getDataAnytime(userid, date1, date2) ;
			 	 datapickeri=incomeDAO.getDataAnytime(userid, date1, date2);
			     renderer.setXTitle( date1+" ��  " +date2 );//����X������
		    	 
		     }
		     seriesp = new XYSeries("֧��");
	            //�������
	            int k=1; 
	            
	            for(Datapicker piker:datapickerp){
	                //��дx,y��ֵ
	                seriesp.add(k,piker.getMoney().doubleValue());
	                k++;
	            }  
	            seriesi = new XYSeries("����");
	            //�������
	            k=1; 
	            for(Datapicker piker:datapickeri){
	                //��дx,y��ֵ
	                seriesi.add(k,piker.getMoney().doubleValue() );
	                k++;
	            }
	            //��Ҫ���Ƶĵ�Ž�dataset��
	            dataset.addSeries(seriesi);
	            dataset.addSeries(seriesp);
			     lChart =(GraphicalView) ChartFactory.getLineChartView(PIData.this, dataset, renderer);
		         chart.addView(lChart);
	         
	         aftert.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					clear();
					if(defaultMonth!=12)
					defaultMonth=defaultMonth+1;
					else{
						defaultMonth=1;
						defaultYear=defaultYear+1;
					}
				 	Intent intenti=new Intent(PIData.this,PIData.class);
				    intenti.putExtra("default",defaultMonth);
					intenti.putExtra("defaulty", defaultYear);
				    intenti.putExtra("cwp.id", userid);
				    startActivity(intenti);
				}
			});
	         beforet.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						clear();
						if(defaultMonth!=1)
						defaultMonth=defaultMonth-1;
						else{
							defaultMonth=12;
							defaultYear=defaultYear-1;
						}
					 	Intent intenti=new Intent(PIData.this,PIData.class);
					    intenti.putExtra("default",defaultMonth);
						intenti.putExtra("defaulty", defaultYear);
					    intenti.putExtra("cwp.id", userid);
					    startActivity(intenti);
					 	
					}
				});
	         
	         anytime.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getAnyDate();
					clear();

			    	 Log.i("====2====",date1+"  222  "+date2);
				 	Intent intenti=new Intent(PIData.this,PIData.class);
				    intenti.putExtra("type",1);
				    intenti.putExtra("date1", date1);
				    intenti.putExtra("date2", date2);
				 	intenti.putExtra("cwp.id", userid);
				    startActivity(intenti);
				}
			});
	         
	         
	    } 
	 	public void clear(){
	 		seriesi.clear();
	 		seriesp.clear();
	 		dataset.removeSeries(seriesi);
	 		dataset.removeSeries(seriesp); 
	 	}
	 	
	 	 
	 	
	 	public void getAnyDate(){
	 		date1=year.getSelectedItem().toString()+"-"+month.getSelectedItem().toString()+"-"+day.getSelectedItem().toString();
	 		date2=yeare.getSelectedItem().toString()+"-"+monthe.getSelectedItem().toString()+"-"+daye.getSelectedItem().toString();

	    	 Log.i("====1====",date1+"  222  "+date2);
	 	}
	 	
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if(keyCode == KeyEvent.KEYCODE_BACK) { //���/����/���η��ؼ�
		    	Intent intent=new Intent(PIData.this,Data.class);
				intent.putExtra("cwp.id",userid);
				startActivity(intent);
		        return true;
		    }
		    return super.onKeyDown(keyCode, event);
		}
		public int getday(int month){
			switch(month){
			case 1:return 31; 
			case 2: return 28;					 
			case 3:return 31 ;
			case 4:return 30; 
			case 5:return 31; 
			case 6:return 30; 
			case 7:return 31; 
			case 8:return 31;  
			case 10:return 31; 
			case 11:return 30; 
			case 12:return 31; 
			
		}
			return 31;
}
		
}		

// 3.3, ��Ҫ���Ƶĵ���ӵ����������
//�ظ�1-3�Ĳ�����Ƶڶ���ϵ�е�

/*  setAxisTitleTextSize(16);// ��������������ı���С
3.    setChartTitleTextSize(20); // ����ͼ������ı���С
4.    setLabelsTextSize(15); // �������ǩ�ı���С
5.    setLegendTextSize(15); // ����ͼ���ı���С
6.    renderer.setChartTitle( "������֧��");//������ͼ����
7.    renderer.setXTitle( "����" );//����X������
8.    renderer.setYTitle( "���" );//����Y������
9.    renderer.setXAxisMin(0.5);//����X�����СֵΪ0.5
10.  renderer.setXAxisMax(5.5);//����X������ֵΪ5
11.  renderer.setYAxisMin(0);//����Y�����СֵΪ0
12.  renderer.setYAxisMax(500);//����Y�����ֵΪ500
13.  renderer.setDisplayChartValues(true);//�����Ƿ��������Ϸ���ʾֵ
14.  renderer.setShowGrid(true);//�����Ƿ���ͼ������ʾ����
15.  renderer.setXLabels(0);//����X����ʾ�Ŀ̶ȱ�ǩ�ĸ���
16.  �����Ҫ��X����ʾ�Զ���ı�ǩ����ô����Ҫ����renderer.setXLabels(0);�������Ҫrenderer.addTextLabel()ѭ�����
17.  renderer.setXLabelsAlign(Align.RIGHT);//���ÿ̶�����X��֮������λ�ù�ϵ
18.  renderer.setYLabelsAlign(Align.RIGHT);//���ÿ̶�����Y��֮������λ�ù�ϵ
19.  renderer.setZoomButtonsVisible(true);//���ÿ�������
20.  renderer.setPanLimits(newdouble[] { 0, 20, 0, 140 });//���������ķ�Χ
21.  renderer.setZoomLimits(newdouble[] { 0.5, 20, 1, 150 });//�������ŵķ�Χ
22.  renderer.setRange(newdouble[]{0d, 5d, 0d, 100d}); //����chart����ͼ��Χ
23.  renderer.setFitLegend(true);// �������ʵ�λ��
24.  renderer.setClickEnabled(true)//�����Ƿ���Ի������Ŵ���С;
25.  Dataset��Render�������ܣ��ο��ֲ�
26.ChartView.repaint()�������»�ͼ������
27.����AChartEngine�ĵ���¼���˫���¼��������¼����������Զ����¼������������Ҫע�����������renderer.setClickEnabled(false);
28.�������ͼ�κ���Σ��������� renderer.setInScroll(true);�����������@gupengno1
29.renderer.setGridColor();//����������ɫ
30.renderer.setAxesColor();//������������ɫ

*/
