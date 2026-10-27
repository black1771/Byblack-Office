package com.byblack.office;

import android.app.*;
import android.os.*;
import android.content.*;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.sl.usermodel.*;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;

public class MainActivity extends Activity {
 TextView output;
 LinearLayout root;
 static final int PICK=100;
 final int LIMIT=150000;

 public void onCreate(Bundle b) {
  super.onCreate(b);
  home();
 }


 // BYBLACK_V5_HOME
 private boolean v5English=false;

 private String v5Text(String tr,String en){
  return v5English?en:tr;
 }

 private android.widget.TextView v5Label(String text,int size,int color){
  android.widget.TextView t=new android.widget.TextView(this);
  t.setText(text);
  t.setTextSize(size);
  t.setTextColor(color);
  t.setPadding(16,12,16,12);
  return t;
 }

 private android.widget.Button v5Button(String text){
  android.widget.Button b=new android.widget.Button(this);
  b.setText(text);
  b.setAllCaps(false);
  return b;
 }


 // BYBLACK_V51_HOME
 private android.widget.TextView v51Text(String text,int size,int color){
  android.widget.TextView t=new android.widget.TextView(this);
  t.setText(text);
  t.setTextSize(size);
  t.setTextColor(color);
  return t;
 }

 private android.widget.Button v51Button(String text){
  android.widget.Button b=new android.widget.Button(this);
  b.setText(text);
  b.setAllCaps(false);
  b.setTextSize(15);
  b.setMinHeight(0);
  b.setMinimumHeight(0);
  b.setPadding(10,6,10,6);
  return b;
 }


 // BYBLACK_V52_HOME
 private int v52dp(int n){
  return (int)(n*getResources().getDisplayMetrics().density+0.5f);
 }

 private android.widget.TextView v52text(String value,int size,int color){
  android.widget.TextView t=new android.widget.TextView(this);
  t.setText(value);
  t.setTextSize(size);
  t.setTextColor(color);
  return t;
 }

 private android.widget.LinearLayout v52column(){
  android.widget.LinearLayout l=new android.widget.LinearLayout(this);
  l.setOrientation(1);
  return l;
 }

 private android.graphics.drawable.GradientDrawable v52bg(int color,int radius){
  android.graphics.drawable.GradientDrawable d=
   new android.graphics.drawable.GradientDrawable();
  d.setColor(color);
  d.setCornerRadius(v52dp(radius));
  return d;
 }

 void home(){
  v5English=getPreferences(0).getBoolean("english",false);

  android.widget.ScrollView scroll=new android.widget.ScrollView(this);
  scroll.setFillViewport(true);
  android.widget.LinearLayout root=v52column();
  root.setPadding(v52dp(18),v52dp(24),v52dp(18),v52dp(20));
  root.setBackgroundColor(0xff101d32);
  scroll.addView(root);

  android.widget.LinearLayout header=
   new android.widget.LinearLayout(this);
  header.setGravity(android.view.Gravity.CENTER_VERTICAL);

  android.widget.TextView title=v52text("BYBLACK Office",25,0xffffffff);
  title.setTypeface(null,1);
  header.addView(title,new android.widget.LinearLayout.LayoutParams(0,-2,1));

  android.widget.Button lang=new android.widget.Button(this);
  lang.setText(v5English?"EN":"TR");
  lang.setAllCaps(false);
  lang.setTextSize(15);
  lang.setTextColor(0xffffffff);
  lang.setBackgroundTintList(
   android.content.res.ColorStateList.valueOf(0xff2563eb));
  header.addView(lang,new android.widget.LinearLayout.LayoutParams(
   v52dp(72),v52dp(52)));
  lang.setOnClickListener(v->{
   v5English=!v5English;
   getPreferences(0).edit().putBoolean("english",v5English).apply();
   home();
  });
  root.addView(header);

  android.widget.TextView version=v52text("V5.2  •  TR / EN",13,0xff9fb5d4);
  version.setPadding(0,v52dp(3),0,v52dp(24));
  root.addView(version);

  android.widget.TextView heading=v52text(
   v5Text("Dosya türleri","File types"),20,0xffffffff);
  heading.setTypeface(null,1);
  root.addView(heading);

  String[] names={"Word","Excel",v5Text("Sunum","Slides"),"PDF"};
  String[] extensions={"DOCX","XLSX","PPTX","PDF"};
  int[] colors={0xff2563eb,0xff087f60,0xffd66a24,0xffca3445};

  for(int r=0;r<2;r++){
   android.widget.LinearLayout line=new android.widget.LinearLayout(this);
   for(int c=0;c<2;c++){
    int index=r*2+c;
    android.widget.LinearLayout tile=v52column();
    tile.setPadding(v52dp(16),v52dp(14),v52dp(12),v52dp(12));
    tile.setBackground(v52bg(colors[index],14));

    android.widget.TextView name=v52text(names[index],18,0xffffffff);
    name.setTypeface(null,1);
    tile.addView(name);

    android.widget.TextView ext=v52text(extensions[index],12,0xffe2e8f0);
    ext.setPadding(0,v52dp(8),0,0);
    tile.addView(ext);

    android.widget.LinearLayout.LayoutParams lp=
     new android.widget.LinearLayout.LayoutParams(0,v52dp(100),1);
    lp.setMargins(v52dp(4),v52dp(6),v52dp(4),v52dp(6));
    line.addView(tile,lp);
    tile.setOnClickListener(v->v5OpenFile());
   }
   root.addView(line);
  }

  android.widget.Button open=new android.widget.Button(this);
  open.setText(v5Text("DOSYA AÇ","OPEN FILE"));
  open.setAllCaps(false);
  open.setTextSize(18);
  open.setTypeface(null,1);
  open.setTextColor(0xff101d32);
  open.setBackgroundTintList(
   android.content.res.ColorStateList.valueOf(0xfff1f5f9));
  android.widget.LinearLayout.LayoutParams openLp=
   new android.widget.LinearLayout.LayoutParams(-1,v52dp(64));
  openLp.setMargins(0,v52dp(20),0,v52dp(16));
  root.addView(open,openLp);
  open.setOnClickListener(v->v5OpenFile());

  android.widget.TextView recent=v52text(
   v5Text("Son açılan dosya","Recently opened"),20,0xffffffff);
  recent.setTypeface(null,1);
  recent.setPadding(0,v52dp(12),0,v52dp(14));
  root.addView(recent);

  String last=getPreferences(0).getString("last_name","");
  String uri=getPreferences(0).getString("last_uri","");

  if(!last.isEmpty()&&!uri.isEmpty()){
   android.widget.TextView item=v52text(last,16,0xffffffff);
   item.setPadding(v52dp(16),v52dp(20),v52dp(12),v52dp(20));
   item.setBackground(v52bg(0xff263c5b,12));
   root.addView(item);
   item.setOnClickListener(v->{
    try{v51Load(android.net.Uri.parse(uri));}
    catch(Exception e){
     android.widget.Toast.makeText(this,e.toString(),1).show();
    }
   });
  }else{
   root.addView(v52text(
    v5Text("Henüz dosya açılmadı.","No recent files yet."),
    15,0xffa7b9d5));
  }

  output=v52text("",14,0xffcbd5e1);
  output.setPadding(0,v52dp(18),0,0);
  root.addView(output);

  setContentView(scroll);
 }

 private void v51Load(android.net.Uri uri){
  if(v53IsPdf(uri)){v53Open(uri);return;}

  output.setText(v5Text("Dosya okunuyor...","Opening file..."));
  new Thread(()->{
   String result;
   try{result=readDocument(uri);}
   catch(Throwable e){result="HATA: "+e;}
   final String shown=result;
   runOnUiThread(()->{
    if(activeWorkbook==null)output.setText(shown);
   });
  }).start();
 }

 private void v5OpenFile(){
  android.content.Intent i=new android.content.Intent(
   android.content.Intent.ACTION_OPEN_DOCUMENT);
  i.setType("*/*");
  i.addCategory(android.content.Intent.CATEGORY_OPENABLE);
  i.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
  startActivityForResult(i,PICK);
 }

 @Override
 protected void onActivityResult(int req,int result,Intent data){
  super.onActivityResult(req,result,data);
  // BYBLACK_FIX9_SAVE_HANDLER
  if(req==101){
   if(result!=RESULT_OK || data==null || data.getData()==null)
    return;
   saveFix9Excel(data.getData());
   return;
  }
  if(req==153){
   if(result==RESULT_OK&&data!=null&&data.getData()!=null)v53Save(data.getData());
   return;
  }
  if(req!=PICK || result!=RESULT_OK || data==null)return;
  Uri uri=data.getData();
  if(uri==null)return;
  if(v53IsPdf(uri)){
   try{
    getContentResolver().takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
   }catch(Exception ignored){}
   getPreferences(0).edit().putString("last_name",v53Name(uri))
    .putString("last_uri",uri.toString()).apply();
   v53Open(uri);return;
  }

  // BYBLACK_V51_RECENT
  try{
   getContentResolver().takePersistableUriPermission(
    uri,android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
   String display="Belge";
   try(android.database.Cursor c=getContentResolver().query(
    uri,null,null,null,null)){
    if(c!=null&&c.moveToFirst()){
     int ix=c.getColumnIndex(
      android.provider.OpenableColumns.DISPLAY_NAME);
     if(ix>=0)display=c.getString(ix);
    }
   }
   getPreferences(0).edit()
    .putString("last_name",display)
    .putString("last_uri",uri.toString()).apply();
  }catch(Exception ignored){}

  output.setText("Dosya okunuyor...");
  try {
   java.io.InputStream test=getAssets().open(
    "xmlbeans/org/apache/xmlbeans/impl/regex/message.properties");
   java.util.Properties props=new java.util.Properties();
   props.load(test);
   test.close();
   output.setText("XMLBEANS ASSET OK: "+props.size());
  }catch(Exception ex){
   output.setText("XMLBEANS ASSET FAIL: "+ex);
  }
  new Thread(()->{
   String resultText;
   try {
    resultText=readDocument(uri);
   } catch(Throwable e) {
    StringWriter sw=new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    Throwable cause=e;
    int depth=0;
    while(cause.getCause()!=null && depth++<15){
     cause=cause.getCause();
    }
    sw.append("\n===== GERCEK KOK HATA =====\n");
    sw.append(cause.toString());
    sw.append("\n");
    for(StackTraceElement frame:cause.getStackTrace()){
     sw.append(frame.toString()).append("\n");
     if(sw.getBuffer().length()>25000)break;
    }
    resultText="MOTOR HATASI:\n"+sw.toString();
   }
   final String show=resultText;
   runOnUiThread(()->output.setText(show));
  }).start();
 }

 String readDocument(Uri uri)throws Exception{
  String name="dosya";
  try(android.database.Cursor c=getContentResolver().query(
   uri,null,null,null,null)){
   if(c!=null && c.moveToFirst()){
    int ix=c.getColumnIndex(
     android.provider.OpenableColumns.DISPLAY_NAME);
    if(ix>=0)name=c.getString(ix);
   }
  }

  String lower=name.toLowerCase(java.util.Locale.ROOT);
  ByteArrayOutputStream buf=new ByteArrayOutputStream();
  try(InputStream in=getContentResolver().openInputStream(uri)){
   if(in==null)throw new IOException("Dosya acilamadi");
   byte[] b=new byte[16384];
   int n;
   while((n=in.read(b))!=-1){
    buf.write(b,0,n);
    if(buf.size()>30*1024*1024)
     throw new IOException("Dosya 30 MB sinirini asti");
   }
  }

  byte[] bytes=buf.toByteArray();
  String content;

  if(lower.endsWith(".xlsx")){
   try(Workbook w=new XSSFWorkbook(
    new ByteArrayInputStream(bytes))){
    content=spreadsheet(w);
   }
  }else if(lower.endsWith(".xls")){
   try(Workbook w=new HSSFWorkbook(
    new ByteArrayInputStream(bytes))){
    content=spreadsheet(w);
   }
  }else if(lower.endsWith(".docx")){
   try(XWPFDocument d=new XWPFDocument(
    new ByteArrayInputStream(bytes))){
    StringBuilder s=new StringBuilder();
    for(XWPFParagraph p:d.getParagraphs())
     s.append(p.getText()).append('\n');
    for(XWPFTable t:d.getTables()){
     for(XWPFTableRow r:t.getRows()){
      for(XWPFTableCell c:r.getTableCells())
       s.append(c.getText()).append(" | ");
      s.append('\n');
     }
    }
    content=s.toString();
   }
  }else if(lower.endsWith(".doc")){
   try(HWPFDocument d=new HWPFDocument(
    new ByteArrayInputStream(bytes))){
    content=d.getRange().text();
   }
  }else if(lower.endsWith(".pptx")){
   try(XMLSlideShow p=new XMLSlideShow(
    new ByteArrayInputStream(bytes))){
    content=slides(p.getSlides());
   }
  }else if(lower.endsWith(".ppt")){
   try(HSLFSlideShow p=new HSLFSlideShow(
    new ByteArrayInputStream(bytes))){
    content=slides(p.getSlides());
   }
  }else if(lower.endsWith(".pdf")){
   PDFBoxResourceLoader.init(getApplicationContext());
   try(PDDocument d=PDDocument.load(bytes)){
    PDFTextStripper stripper=new PDFTextStripper();
    stripper.setEndPage(Math.min(30,d.getNumberOfPages()));
    content="PDF sayfa sayisi: "+d.getNumberOfPages()+
     "\n\n"+stripper.getText(d);
   }
  }else{
   content=new String(bytes,java.nio.charset.StandardCharsets.UTF_8);
  }

  if(content.length()>LIMIT)
   content=content.substring(0,LIMIT)+
    "\n\n[Goruntuleme sinirina ulasildi]";

  if(name.toLowerCase(java.util.Locale.ROOT).endsWith(".xlsx")
    || name.toLowerCase(java.util.Locale.ROOT).endsWith(".xls")){
    final byte[] excelData=bytes;
    final String excelName=name;
    runOnUiThread(()->{
     try{showFix8Table(excelData,excelName);}
     catch(Exception ex){
      android.widget.Toast.makeText(this,
       "FIX8: "+ex.getMessage(),
       android.widget.Toast.LENGTH_LONG).show();
     }
    });
   }
   return "DOSYA: "+name+"\n\n"+content;
 }

 String spreadsheet(Workbook w){
  StringBuilder s=new StringBuilder();
  DataFormatter formatter=new DataFormatter();
  FormulaEvaluator evaluator=w.getCreationHelper()
   .createFormulaEvaluator();

  for(int sh=0;sh<w.getNumberOfSheets();sh++){
   org.apache.poi.ss.usermodel.Sheet sheet=w.getSheetAt(sh);
   s.append("\n===== SAYFA: ")
    .append(sheet.getSheetName())
    .append(" =====\n");

   int count=0;
   for(Row row:sheet){
    if(count++>=300){
     s.append("[300 satir siniri]\n");
     break;
    }
    s.append(row.getRowNum()+1).append(" | ");
    int max=Math.min(30,Math.max(0,row.getLastCellNum()));
    for(int col=0;col<max;col++){
     Cell cell=row.getCell(col);
     if(cell!=null){
      try{
       s.append(formatter.formatCellValue(cell,evaluator));
      }catch(Exception e){
       s.append(formatter.formatCellValue(cell));
      }
     }
     s.append(" | ");
    }
    s.append('\n');
   }
  }
  return s.toString();
 }

 String slides(
  java.util.List<? extends Slide<?,?>> slideList){
  StringBuilder s=new StringBuilder();
  int i=0;
  for(Slide<?,?> slide:slideList){
   s.append("\n===== SLAYT ")
    .append(++i).append(" =====\n");
   for(Object shape:slide.getShapes()){
    if(shape instanceof TextShape){
     s.append(((TextShape<?,?>)shape).getText())
      .append('\n');
    }
   }
  }
  return s.toString();
 }


 // BYBLACK_FIX9_LISTENER
 private static class Fix9SheetListener implements android.widget.AdapterView.OnItemSelectedListener {
  private final MainActivity activity;
  Fix9SheetListener(MainActivity a){activity=a;}
  public void onNothingSelected(android.widget.AdapterView<?> p){}
  public void onItemSelected(android.widget.AdapterView<?> p,android.view.View v,int position,long id){
   if(position!=activity.activeSheetIndex){
    activity.activeSheetIndex=position;
    activity.renderFix9Sheet();
   }
  }
 }
 // BYBLACK_V42_EDITOR
 // BYBLACK_V41
 private android.widget.EditText v41Editor;
 private android.widget.TextView v41Address;
 private int v41Row=0,v41Col=0;
 private void v41Select(org.apache.poi.ss.usermodel.Sheet sh,int r,int c){
  v41Row=r;v41Col=c;
  org.apache.poi.ss.usermodel.Row row=sh.getRow(r);
  org.apache.poi.ss.usermodel.Cell cell=row==null?null:row.getCell(c);
  v41Address.setText(fix9Column(c)+(r+1));
  String value="";
  if(cell!=null){
   if(cell.getCellType()==org.apache.poi.ss.usermodel.CellType.FORMULA)
    value="="+cell.getCellFormula();
   else value=new org.apache.poi.ss.usermodel.DataFormatter().formatCellValue(cell);
  }
  v41Editor.setText(value);
 }
 // BYBLACK_FIX9_EDITOR
 private org.apache.poi.ss.usermodel.Workbook activeWorkbook;
 private String activeExcelName="BYBLACK.xlsx";
 private int activeSheetIndex=0;
 private boolean excelChanged=false;

 private void showFix8Table(byte[] data,String filename) throws Exception {
  closeFix9();
  if(filename.toLowerCase(java.util.Locale.ROOT).endsWith(".xlsx"))
   activeWorkbook=new org.apache.poi.xssf.usermodel.XSSFWorkbook(
    new java.io.ByteArrayInputStream(data));
  else
   activeWorkbook=new org.apache.poi.hssf.usermodel.HSSFWorkbook(
    new java.io.ByteArrayInputStream(data));
  activeExcelName=filename;
  activeSheetIndex=0;
  excelChanged=false;
  renderFix9Sheet();
 }

 private void renderFix9Sheet(){
  if(activeWorkbook==null)return;
  final org.apache.poi.ss.usermodel.Workbook wb=activeWorkbook;
  android.widget.LinearLayout layout=
   new android.widget.LinearLayout(this);
  layout.setOrientation(1);
  layout.setBackgroundColor(0xfff8fafc);

  // BYBLACK_V4_UI
  android.widget.TextView title=new android.widget.TextView(this);
  title.setText("  BYBLACK OFFICE  |  "+activeExcelName);
  title.setTextSize(19);
  title.setTextColor(0xffffffff);
  title.setBackgroundColor(0xff142d4e);
  title.setPadding(12,22,12,22);
  layout.addView(title);

  android.widget.LinearLayout toolbar=
   new android.widget.LinearLayout(this);
  toolbar.setOrientation(0);

  android.widget.Button back=new android.widget.Button(this);
  back.setText("← DOSYALAR");
  toolbar.addView(back,
   new android.widget.LinearLayout.LayoutParams(0,-2,1));
  back.setOnClickListener(v->{
   if(excelChanged){
    new android.app.AlertDialog.Builder(this)
     .setTitle("Kaydedilmemis degisiklik")
     .setMessage("Kaydetmeden cikilsin mi?")
     .setNegativeButton("Iptal",null)
     .setPositiveButton("Cik",(d,w)->{
      closeFix9();home();
     }).show();
   }else{closeFix9();home();}
  });

  android.widget.Button save=new android.widget.Button(this);
  save.setText("KAYDET ⬇");
  toolbar.addView(save,
   new android.widget.LinearLayout.LayoutParams(0,-2,1));
  save.setOnClickListener(v->{
   android.content.Intent i=new android.content.Intent(
    android.content.Intent.ACTION_CREATE_DOCUMENT);
   i.addCategory(android.content.Intent.CATEGORY_OPENABLE);
   boolean xlsx=wb instanceof org.apache.poi.xssf.usermodel.XSSFWorkbook;
   i.setType(xlsx?
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
    "application/vnd.ms-excel");
   i.putExtra(android.content.Intent.EXTRA_TITLE,
    "DUZENLENMIS_"+activeExcelName);
   startActivityForResult(i,101);
  });
  layout.addView(toolbar);

  android.widget.Spinner spinner=new android.widget.Spinner(this);
  String[] names=new String[wb.getNumberOfSheets()];
  for(int i=0;i<names.length;i++)
   names[i]=wb.getSheetName(i);
  android.widget.ArrayAdapter<String> adapter=
   new android.widget.ArrayAdapter<String>(this,
    android.R.layout.simple_spinner_dropdown_item,names);
  spinner.setAdapter(adapter);
  layout.addView(spinner);
  spinner.setSelection(activeSheetIndex);
  spinner.setOnItemSelectedListener(new Fix9SheetListener(this));

  android.widget.LinearLayout formula=new android.widget.LinearLayout(this);
  formula.setOrientation(0);
  v41Address=new android.widget.TextView(this);
  v41Address.setText("A1");
  v41Address.setTextSize(16);
  v41Address.setPadding(12,15,12,10);
  formula.addView(v41Address,new android.widget.LinearLayout.LayoutParams(100,-2));
  v41Editor=new android.widget.EditText(this);
  v41Editor.setSingleLine(true);
  v41Editor.setTextSize(15);
  formula.addView(v41Editor,new android.widget.LinearLayout.LayoutParams(0,-2,1));
  android.widget.Button apply=new android.widget.Button(this);
  apply.setText("OK");
  formula.addView(apply);
  apply.setOnClickListener(v->{
   org.apache.poi.ss.usermodel.Sheet sh=wb.getSheetAt(activeSheetIndex);
   org.apache.poi.ss.usermodel.Row row=sh.getRow(v41Row);
   if(row==null)row=sh.createRow(v41Row);
   org.apache.poi.ss.usermodel.Cell cell=row.getCell(v41Col);
   if(cell==null)cell=row.createCell(v41Col);
   String val=v41Editor.getText().toString();
   if(cell.getCellType()==org.apache.poi.ss.usermodel.CellType.FORMULA){
    android.widget.Toast.makeText(this,"Formul korundu",0).show();
    return;
   }
   if(cell.getCellType()==org.apache.poi.ss.usermodel.CellType.NUMERIC){
    try{cell.setCellValue(Double.parseDouble(val));}
    catch(Exception ex){cell.setCellValue(val);}
   }else cell.setCellValue(val);
   excelChanged=true;
   renderFix9Sheet();
  });
  layout.addView(formula);
  android.widget.HorizontalScrollView horizontal=
   new android.widget.HorizontalScrollView(this);
  android.widget.ScrollView vertical=
   new android.widget.ScrollView(this);
  android.widget.LinearLayout table=
   new android.widget.LinearLayout(this);
  table.setOrientation(1);

  org.apache.poi.ss.usermodel.Sheet sheet=
   wb.getSheetAt(activeSheetIndex);
  org.apache.poi.ss.usermodel.DataFormatter fmt=
   new org.apache.poi.ss.usermodel.DataFormatter();

  int maxCols=8;
  for(org.apache.poi.ss.usermodel.Row row:sheet){
   maxCols=Math.max(maxCols,Math.max(0,row.getLastCellNum()));
   if(row.getRowNum()>300)break;
  }
  maxCols=Math.min(30,maxCols);

  for(int ri=-1;ri<=Math.min(300,sheet.getLastRowNum());ri++){
   android.widget.LinearLayout line=
    new android.widget.LinearLayout(this);
   line.setOrientation(0);

   for(int ci=-1;ci<maxCols;ci++){
    final int rowIndex=ri,colIndex=ci;
    org.apache.poi.ss.usermodel.Row row=
     ri>=0?sheet.getRow(ri):null;
    org.apache.poi.ss.usermodel.Cell cell=
     row!=null&&ci>=0?row.getCell(ci):null;

    String value="";
    if(ri==-1)value=ci==-1?"":fix9Column(ci);
    else if(ci==-1)value=String.valueOf(ri+1);
    else if(cell!=null){
     try{value=fmt.formatCellValue(cell);}
     catch(Exception ignored){}
    }

    android.widget.TextView view=new android.widget.TextView(this);
    view.setText(value);
    view.setTextSize(15);
    view.setSingleLine(false);
    view.setMaxLines(2);
    view.setGravity(android.view.Gravity.CENTER_VERTICAL);
    view.setTextColor(0xff142a40);
    view.setPadding(12,10,12,10);
    view.setBackgroundColor(ri==-1||ci==-1?
     0xffd7e2ee:ri%2==0?0xffffffff:0xffedf3f8);

    android.widget.LinearLayout.LayoutParams lp=
     new android.widget.LinearLayout.LayoutParams(
      ci==-1?70:ci==0?220:ci==1?230:190,90);
    lp.setMargins(1,1,1,1);
    line.addView(view,lp);

    if(ri>=0&&ci>=0)
     view.setOnClickListener(v->{
      v41Select(sheet,rowIndex,colIndex);
      v41Editor.requestFocus();
      android.view.inputmethod.InputMethodManager im=
       (android.view.inputmethod.InputMethodManager)
        getSystemService(INPUT_METHOD_SERVICE);
      if(im!=null)
       im.showSoftInput(v41Editor,
        android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
     });
   }
   table.addView(line);
  }

  vertical.addView(table);
  horizontal.addView(vertical);
  layout.addView(horizontal,
   new android.widget.LinearLayout.LayoutParams(-1,0,1));
  setContentView(layout);
 }

 private String fix9Column(int index){
  StringBuilder s=new StringBuilder();
  for(int n=index+1;n>0;n=(n-1)/26)
   s.insert(0,(char)('A'+(n-1)%26));
  return s.toString();
 }

 private void editFix9Cell(org.apache.poi.ss.usermodel.Sheet sheet,
  int ri,int ci){
  org.apache.poi.ss.usermodel.Row row=sheet.getRow(ri);
  org.apache.poi.ss.usermodel.Cell cell=
   row==null?null:row.getCell(ci);

  if(cell!=null&&cell.getCellType()==
   org.apache.poi.ss.usermodel.CellType.FORMULA){
   new android.app.AlertDialog.Builder(this)
    .setTitle(fix9Column(ci)+(ri+1))
    .setMessage("Formul: ="+cell.getCellFormula()+
     "\nBu surumde formul hucreleri korunur.")
    .setPositiveButton("Tamam",null).show();
   return;
  }

  String old=cell==null?"":new
   org.apache.poi.ss.usermodel.DataFormatter().formatCellValue(cell);

  android.widget.EditText input=new android.widget.EditText(this);
  input.setSingleLine(true);
  input.setText(old);
  input.setSelectAllOnFocus(true);

  new android.app.AlertDialog.Builder(this)
   .setTitle("Hucre "+fix9Column(ci)+(ri+1))
   .setView(input)
   .setNegativeButton("Iptal",null)
   .setPositiveButton("Uygula",(d,w)->{
    org.apache.poi.ss.usermodel.Row r=sheet.getRow(ri);
    if(r==null)r=sheet.createRow(ri);
    org.apache.poi.ss.usermodel.Cell c=r.getCell(ci);
    if(c==null)c=r.createCell(ci);
    c.setCellValue(input.getText().toString());
    excelChanged=true;
    renderFix9Sheet();
   }).show();
 }

 private void saveFix9Excel(android.net.Uri uri){
  if(activeWorkbook==null)return;
  new Thread(()->{
   try(java.io.OutputStream out=
    getContentResolver().openOutputStream(uri)){
    if(out==null)throw new java.io.IOException("Kayit acilamadi");
    activeWorkbook.write(out);
    runOnUiThread(()->{
     excelChanged=false;
     android.widget.Toast.makeText(this,
      "Excel kaydedildi",android.widget.Toast.LENGTH_LONG).show();
    });
   }catch(Exception e){
    runOnUiThread(()->new android.app.AlertDialog.Builder(this)
     .setTitle("Kayit hatasi")
     .setMessage(e.toString())
     .setPositiveButton("Tamam",null).show());
   }
  }).start();
 }

 private void closeFix9(){
  if(activeWorkbook!=null){
   try{activeWorkbook.close();}catch(Exception ignored){}
   activeWorkbook=null;
  }
  excelChanged=false;
 }

 // BYBLACK_V53_PDF
 private android.os.ParcelFileDescriptor v53Fd;
 private android.graphics.pdf.PdfRenderer v53Pdf;
 private android.widget.ImageView v53Image;
 private android.widget.TextView v53Status;
 private int v53Page=0;
 private float v53Zoom=1f;
 private final java.util.HashMap<Integer,java.util.ArrayList<V53Note>> v53Notes=new java.util.HashMap<>();
 private static class V53Note{
  float x,y; String text;
  V53Note(float xx,float yy,String t){x=xx;y=yy;text=t;}
 }
 private boolean v53IsPdf(android.net.Uri uri){
  return v53Name(uri).toLowerCase(java.util.Locale.ROOT).endsWith(".pdf");
 }
 private String v53Name(android.net.Uri uri){
  try(android.database.Cursor c=getContentResolver().query(uri,null,null,null,null)){
   if(c!=null&&c.moveToFirst()){
    int i=c.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);
    if(i>=0)return c.getString(i);
   }
  }catch(Exception ignored){}
  return uri.getLastPathSegment()==null?"document.pdf":uri.getLastPathSegment();
 }
 private String v53T(String tr,String en){return v5English?en:tr;}
 private void v53Close(){
  if(v53Pdf!=null){try{v53Pdf.close();}catch(Exception ignored){}v53Pdf=null;}
  if(v53Fd!=null){try{v53Fd.close();}catch(Exception ignored){}v53Fd=null;}
 }
 private void v53Error(Throwable e){
  new android.app.AlertDialog.Builder(this).setTitle("PDF")
   .setMessage(e.toString()).setPositiveButton("OK",null).show();
 }
 private void v53Open(android.net.Uri uri){
  try{
   v53Close();v53Notes.clear();v53Page=0;v53Zoom=1f;
   v53Fd=getContentResolver().openFileDescriptor(uri,"r");
   if(v53Fd==null)throw new java.io.IOException("PDF acilamadi");
   v53Pdf=new android.graphics.pdf.PdfRenderer(v53Fd);
   v53Build();
  }catch(Exception e){v53Close();v53Error(e);}
 }
 private int v53dp(int v){return (int)(v*getResources().getDisplayMetrics().density+0.5f);}
 private android.widget.Button v53Btn(String text){
  android.widget.Button b=new android.widget.Button(this);
  b.setText(text);b.setAllCaps(false);b.setTextSize(14);return b;
 }
 private void v53Build(){
  android.widget.LinearLayout root=new android.widget.LinearLayout(this);
  root.setOrientation(1);root.setBackgroundColor(0xff101d32);
  android.widget.LinearLayout top=new android.widget.LinearLayout(this);
  android.widget.Button back=v53Btn(v53T("Geri","Back"));
  android.widget.Button save=v53Btn(v53T("PDF Kaydet","Save PDF"));
  top.addView(back,new android.widget.LinearLayout.LayoutParams(0,v53dp(56),1));
  top.addView(save,new android.widget.LinearLayout.LayoutParams(0,v53dp(56),1));
  root.addView(top);
  back.setOnClickListener(v->{v53Close();home();});
  save.setOnClickListener(v->{
   android.content.Intent i=new android.content.Intent(android.content.Intent.ACTION_CREATE_DOCUMENT);
   i.addCategory(android.content.Intent.CATEGORY_OPENABLE);
   i.setType("application/pdf");
   i.putExtra(android.content.Intent.EXTRA_TITLE,"BYBLACK-edited.pdf");
   startActivityForResult(i,153);
  });
  android.widget.LinearLayout controls=new android.widget.LinearLayout(this);
  android.widget.Button prev=v53Btn("◀");android.widget.Button next=v53Btn("▶");
  android.widget.Button minus=v53Btn("−");android.widget.Button plus=v53Btn("+");
  v53Status=new android.widget.TextView(this);v53Status.setTextColor(-1);
  v53Status.setGravity(android.view.Gravity.CENTER);v53Status.setTextSize(14);
  controls.addView(prev,new android.widget.LinearLayout.LayoutParams(0,v53dp(52),1));
  controls.addView(v53Status,new android.widget.LinearLayout.LayoutParams(0,v53dp(52),2));
  controls.addView(next,new android.widget.LinearLayout.LayoutParams(0,v53dp(52),1));
  controls.addView(minus,new android.widget.LinearLayout.LayoutParams(0,v53dp(52),1));
  controls.addView(plus,new android.widget.LinearLayout.LayoutParams(0,v53dp(52),1));
  root.addView(controls);
  android.widget.TextView help=new android.widget.TextView(this);
  help.setText(v53T("Yazi eklemek icin sayfaya dokun","Tap page to add text"));
  help.setTextColor(0xffb9c9de);help.setGravity(android.view.Gravity.CENTER);
  help.setPadding(0,v53dp(6),0,v53dp(8));root.addView(help);
  android.widget.ScrollView sc=new android.widget.ScrollView(this);
  android.widget.HorizontalScrollView hs=new android.widget.HorizontalScrollView(this);
  v53Image=new android.widget.ImageView(this);
  v53Image.setAdjustViewBounds(true);
  v53Image.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
  hs.addView(v53Image);sc.addView(hs);
  root.addView(sc,new android.widget.LinearLayout.LayoutParams(-1,0,1));
  prev.setOnClickListener(v->{if(v53Page>0){v53Page--;v53Render();}});
  next.setOnClickListener(v->{if(v53Page+1<v53Pdf.getPageCount()){v53Page++;v53Render();}});
  minus.setOnClickListener(v->{v53Zoom=Math.max(0.5f,v53Zoom/1.25f);v53Render();});
  plus.setOnClickListener(v->{v53Zoom=Math.min(3f,v53Zoom*1.25f);v53Render();});
  v53Image.setOnTouchListener((v,event)->{
   if(event.getAction()!=android.view.MotionEvent.ACTION_UP)return true;
   float x=Math.max(0,Math.min(1,event.getX()/v.getWidth()));
   float y=Math.max(0,Math.min(1,event.getY()/v.getHeight()));
   v53Add(x,y);return true;
  });
  setContentView(root);v53Render();
 }
 private void v53Add(float x,float y){
  android.widget.EditText input=new android.widget.EditText(this);
  input.setSingleLine(false);input.setHint(v53T("Eklenecek yazi","Text to add"));
  new android.app.AlertDialog.Builder(this)
   .setTitle(v53T("PDF'ye yazi ekle","Add text to PDF"))
   .setView(input).setNegativeButton(v53T("Iptal","Cancel"),null)
   .setPositiveButton(v53T("Ekle","Add"),(d,w)->{
    String text=input.getText().toString();if(text.trim().isEmpty())return;
    java.util.ArrayList<V53Note> list=v53Notes.get(v53Page);
    if(list==null){list=new java.util.ArrayList<>();v53Notes.put(v53Page,list);}
    list.add(new V53Note(x,y,text));v53Render();
   }).show();
 }
 private void v53PaintNotes(android.graphics.Canvas canvas,int index,int width,int height){
  java.util.ArrayList<V53Note> notes=v53Notes.get(index);
  if(notes==null)return;
  android.graphics.Paint paint=new android.graphics.Paint(3);
  paint.setColor(android.graphics.Color.rgb(18,43,90));
  paint.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
  paint.setTextSize(Math.max(12,width*0.022f));
  for(V53Note note:notes){
   String[] lines=note.text.split("\\n",-1);
   for(int i=0;i<lines.length;i++)
    canvas.drawText(lines[i],note.x*width,Math.min(height-4,note.y*height+i*paint.getTextSize()*1.3f),paint);
  }
 }
 private void v53Render(){
  if(v53Pdf==null)return;
  try(android.graphics.pdf.PdfRenderer.Page p=v53Pdf.openPage(v53Page)){
   int width=Math.max(1,Math.round(900*v53Zoom));
   int height=Math.max(1,Math.round((float)width*p.getHeight()/p.getWidth()));
   android.graphics.Bitmap bitmap=android.graphics.Bitmap.createBitmap(width,height,android.graphics.Bitmap.Config.ARGB_8888);
   bitmap.eraseColor(-1);
   p.render(bitmap,null,null,android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
   v53PaintNotes(new android.graphics.Canvas(bitmap),v53Page,width,height);
   v53Image.setImageBitmap(bitmap);
   v53Image.setLayoutParams(new android.widget.FrameLayout.LayoutParams(width,height));
   v53Status.setText((v53Page+1)+" / "+v53Pdf.getPageCount());
  }catch(Exception e){v53Error(e);}
 }
 private void v53Save(android.net.Uri uri){
  if(v53Pdf==null)return;
  try{
   android.graphics.pdf.PdfDocument document=new android.graphics.pdf.PdfDocument();
   try{
    for(int index=0;index<v53Pdf.getPageCount();index++){
     try(android.graphics.pdf.PdfRenderer.Page page=v53Pdf.openPage(index)){
      int w=page.getWidth(),h=page.getHeight();
      android.graphics.Bitmap bm=android.graphics.Bitmap.createBitmap(w*2,h*2,android.graphics.Bitmap.Config.ARGB_8888);
      bm.eraseColor(-1);
      page.render(bm,null,null,android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_PRINT);
      android.graphics.pdf.PdfDocument.Page out=document.startPage(
       new android.graphics.pdf.PdfDocument.PageInfo.Builder(w,h,index+1).create());
      android.graphics.Canvas canvas=out.getCanvas();
      canvas.drawBitmap(bm,null,new android.graphics.RectF(0,0,w,h),null);
      v53PaintNotes(canvas,index,w,h);
      document.finishPage(out);bm.recycle();
     }
    }
    try(java.io.OutputStream stream=getContentResolver().openOutputStream(uri,"w")){
     if(stream==null)throw new java.io.IOException("Kayit acilamadi");
     document.writeTo(stream);
    }
   }finally{document.close();}
   android.widget.Toast.makeText(this,v53T("PDF kaydedildi","PDF saved"),1).show();
  }catch(Exception e){v53Error(e);}
 }

}
