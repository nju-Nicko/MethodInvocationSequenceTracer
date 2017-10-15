package presentation;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import util.InitialValues;
import vo.common_obj.MethodInfo;
import vo.info_obj.FinalInfo;

import business_logic.ast.ASTCreator;
import business_logic.execute.SymbolicExecutor;
import business_logic.tested_class_info.TestedClassInfoGetter;
import business_logic.tested_class_info.TestedClassInfoVisitor;

/**
 * 主窗体
 * @author 倪陆章
 *
 */
public class MainWindow {

	private Frame frame;
	private TextArea sourcecode, console;
	private ScrollPane sp;
	private Label l2;
	private CheckboxGroup cg;
	private CompilationUnit root;
	private String cur_file_path=null;  //被测类的路径
	private String packageAndName=null;
	private String packageInfo=null;
	private String projectRootPath;
	private ArrayList<ImportDeclaration> imports;
	
	public ArrayList<MethodInfo> all;
//	private int loop_times=1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new Frame("Symbolic Executor");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		frame.setBounds(100, 100, 700, 600);
		frame.setLayout(new GridLayout(2,1));
		frame.setLocationRelativeTo(null);
		
		MenuBar mb=new MenuBar();
		
		Menu file=new Menu("File");
        mb.add(file);
        
        Menu setting=new Menu("Setting");
        mb.add(setting);
        
        Menu help=new Menu("Help");
        mb.add(help);
        
        MenuItem open=new MenuItem("Open");
        file.add(open);
        open.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				  FileDialog fd=new FileDialog(frame, "open", FileDialog.LOAD);
				  fd.setVisible(true);
				  String path=fd.getDirectory()+fd.getFile();
				  if(!path.equals("nullnull")){
					  loadfile(path);
					  frame.setTitle("Symbolic Executor"+": "+path);
				  	}
				}
        });
        
        MenuItem refresh=new MenuItem("Refresh");
        refresh.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				refresh();
			}
        	
        });
        file.add(refresh);
        
        MenuItem clear=new MenuItem("Clear");
        clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clear();
				frame.setTitle("Symbolic Executor");
			}
        	
        });
        file.add(clear);
        
        file.addSeparator();
        
        MenuItem exit=new MenuItem("Exit");
        exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
        	
        });
        file.add(exit);
        
        MenuItem loops=new MenuItem("param");
        loops.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				final Dialog loopssetting=new Dialog(frame, "params setting");
				loopssetting.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosing(WindowEvent arg0) {
						loopssetting.dispose();
					}
				});
				loopssetting.setSize(200, 150);
				loopssetting.setLocationRelativeTo(null);
				
				loopssetting.setLayout(new GridLayout(4, 1));
				final Label ins=new Label("Loop times: "+InitialValues.loop_times);
				loopssetting.add(ins);
				final TextField enterarea=new TextField();
				enterarea.addKeyListener(new KeyListener(){

					@Override
					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						if(KeyEvent.getKeyText(arg0.getKeyCode()).equals("Enter")){
							try{
								InitialValues.loop_times=Integer.parseInt(enterarea.getText());
								InitialValues.loop_times=(InitialValues.loop_times>=0)?InitialValues.loop_times:1;
								ins.setText("Loop times: "+InitialValues.loop_times);
							}catch(NumberFormatException e){
								e.printStackTrace();
							}
						}
					}

					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				loopssetting.add(enterarea);
				final Label arrc=new Label("Array max Columns: "+InitialValues.arrCols);
				loopssetting.add(arrc);
				final TextField enterarea2=new TextField();
				enterarea2.addKeyListener(new KeyListener(){

					@Override
					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						if(KeyEvent.getKeyText(arg0.getKeyCode()).equals("Enter")){
							try{
								InitialValues.arrCols=Integer.parseInt(enterarea2.getText());
								InitialValues.arrCols=(InitialValues.arrCols>=1)?InitialValues.arrCols:10;
								arrc.setText("Array max Columns: "+InitialValues.arrCols);
							}catch(NumberFormatException e){
								e.printStackTrace();
							}
						}
					}

					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				loopssetting.add(enterarea2);
				loopssetting.setModal(true);
				loopssetting.setVisible(true);
			}
        	
        });
        setting.add(loops);
        
		frame.setMenuBar(mb);
		
		Panel upper=new Panel();  //上层面板
		frame.add(upper);
		
		Panel toolpane=new Panel(); //工具栏面板
		toolpane.setBackground(new Color(230, 230, 230));
		toolpane.setLayout(null);
		toolpane.setSize(0, 25);
		
		ToolLabel tlopen=new ToolLabel(MainWindow.class.getResource("/img/folder_open.png"));
		tlopen.setLocation(8, 5);
		tlopen.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent e){
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			public void mouseClicked(MouseEvent e){
				  FileDialog fd=new FileDialog(frame, "open", FileDialog.LOAD);
				  fd.setVisible(true);
				  String path=fd.getDirectory()+fd.getFile();
				  if(!path.equals("nullnull")){
					  loadfile(path);
					  frame.setTitle("Symbolic Executor"+": "+path);
				  	}
			}
		});
		toolpane.add(tlopen);
		
		ToolLabel tlrefresh=new ToolLabel(MainWindow.class.getResource("/img/reload.png"));
		tlrefresh.setLocation(34, 6);
		tlrefresh.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent e){
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			public void mouseClicked(MouseEvent e){
				refresh();
			}
		});
		toolpane.add(tlrefresh);
		
		ToolLabel tlclear=new ToolLabel(MainWindow.class.getResource("/img/page_text_close.png"));
		tlclear.setLocation(60, 6);
		tlclear.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent e){
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			public void mouseClicked(MouseEvent e){
				clear();
				frame.setTitle("Symbolic Executor");
			}
		});
		toolpane.add(tlclear);
		
		sourcecode=new TextArea();
		sourcecode.setEditable(false);
		sourcecode.setBackground(Color.white);
		sourcecode.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
        upper.add(toolpane);
        upper.add(sourcecode);
        
        GridBagLayout ugbl=new GridBagLayout();
		GridBagConstraints us= new GridBagConstraints();

		us.fill = GridBagConstraints.BOTH;
		us.gridwidth=GridBagConstraints.REMAINDER;
		us.gridheight=1;
		ugbl.setConstraints(toolpane, us);
		
		us.fill = GridBagConstraints.BOTH;
		us.gridwidth=GridBagConstraints.REMAINDER;
		us.gridheight=GridBagConstraints.REMAINDER;
		us.weightx = 1;
		us.weighty=1;
		ugbl.setConstraints(sourcecode, us);
		
		upper.setLayout(ugbl);
		
		Panel panel=new Panel();       //下层面板
		frame.add(panel);
		panel.setLayout(new GridLayout(1,2));
		
		Panel lefttop=new Panel();     //方法选择提示面板
		lefttop.setBackground(new Color(220, 220, 220));
		lefttop.setLayout(new BorderLayout());
		Label l1=new Label("Methods");
		l1.setFont(new Font("微软雅黑", Font.BOLD, 12));
		lefttop.add(BorderLayout.WEST, l1);
		l2=new Label("Execute");
		l2.setEnabled(false);
		l2.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0){
				String mi=cg.getSelectedCheckbox().getLabel();
				mi=mi.replace(" ", "");
				int index1=mi.indexOf("(");
				String mname=mi.substring(0, index1);
				int index2=mi.indexOf(")");
				String ps=mi.substring(index1+1, index2);			
				MethodInfo minfo=null;
				String tmp1=packageAndName.substring(0, packageAndName.length()-5);
				String tmp2=tmp1.replace("\\", ".");
				if(ps.equals("")){
					minfo=new MethodInfo(tmp2, mname);
				}
				else{
					String[] split=ps.split(",");
					ArrayList<String> pts=new ArrayList<String>();
					for(int i=0; i<=split.length-1; i++)
						pts.add(split[i]);
					minfo=new MethodInfo(tmp2, mname, pts);
				}
				console.append("对方法"+tmp2+"."+mi+"符号执行的结果如下：\n");

				SymbolicExecutor se=new SymbolicExecutor(root, minfo, all, projectRootPath, imports, packageInfo);
				FinalInfo fi=se.execute();

				console.append(fi.toString()+"\n");
			}
		});
		lefttop.add(BorderLayout.EAST, l2);

		sp=new ScrollPane();  //方法选择面板
	
		Panel left=new Panel();         //下左容器面板
		left.add(lefttop);
		left.add(sp);
		
		GridBagLayout gl=new GridBagLayout();
		GridBagConstraints s= new GridBagConstraints();
		
		s.fill = GridBagConstraints.BOTH;
		s.gridwidth=GridBagConstraints.REMAINDER;
		s.gridheight=1;
		gl.setConstraints(lefttop, s);
		
		s.fill = GridBagConstraints.BOTH;
		s.gridwidth=GridBagConstraints.REMAINDER;
		s.gridheight=GridBagConstraints.REMAINDER;
		s.weightx = 1;
		s.weighty=1;
		gl.setConstraints(sp, s);

		left.setLayout(gl);		
		
		panel.add(left);
		
		Panel righttop=new Panel();  //控制台提示面板
		righttop.setBackground(new Color(220, 220, 220));
		righttop.setLayout(new BorderLayout());
		Label l3=new Label("Console");
		l3.setFont(new Font("微软雅黑", Font.BOLD, 12));
		righttop.add(BorderLayout.WEST, l3);
		
		console=new TextArea();  //控制台
		console.setEditable(false);
		console.setBackground(Color.white);
		console.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		
		Panel right=new Panel();  //下右面板
		right.add(righttop);
		right.add(console);
		
		GridBagLayout gl2=new GridBagLayout();
		GridBagConstraints s2= new GridBagConstraints();
		
		s2.fill = GridBagConstraints.BOTH;
		s2.gridwidth=GridBagConstraints.REMAINDER;
		s2.gridheight=1;
		gl2.setConstraints(righttop, s2);
		
		s2.fill = GridBagConstraints.BOTH;
		s2.gridwidth=GridBagConstraints.REMAINDER;
		s2.gridheight=GridBagConstraints.REMAINDER;
		s2.weightx = 1;
		s2.weighty=1;
		gl2.setConstraints(console, s2);

		right.setLayout(gl2);		
		
		panel.add(right);
	}
	
	/**
	 * 根据被测试的类的路径和类的包名计算类的项目根路径
	 * @return 被测项目的根路径
	 */
	public String getProjectRootPath(){
		String class_path=cur_file_path;
		String pack=packageAndName;
		int index=class_path.lastIndexOf(pack);
		String root=class_path.substring(0, index);
		return root;
	}
	
	private void loadfile(String path){
		  clear();		
          cur_file_path=path;
		  try {
			  BufferedReader br=new BufferedReader(new FileReader(path));
			  StringBuilder input=new StringBuilder("");
			  String line=null;
			  while((line=br.readLine())!=null){
				  input=input.append(line).append("\n");
			  }		
			  sourcecode.setText(input.toString());
			  br.close();
		  } catch (FileNotFoundException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  console.setText("类加载完毕！\n\n");
	  
		  ASTCreator ac=new ASTCreator();
		  root=ac.createAST(path);
	  
		  TestedClassInfoGetter mg=new TestedClassInfoGetter();
		  TestedClassInfoVisitor mvisitor=mg.getMethods(root);
		  packageAndName=mvisitor.getPackageAndName();
		  projectRootPath=getProjectRootPath();
		  packageInfo=mvisitor.getPackageInfo();
		  imports=mvisitor.getImports();
		  ArrayList<MethodInfo> methods=mvisitor.getMethods();
		  all=mvisitor.getMethods();
		  Panel panel=new Panel(new GridLayout(methods.size(), 1));
		  cg=new CheckboxGroup();
		  for(MethodInfo mi: methods){
			  String mn=mi.method_name+"(";
			  for(int i=0; i<=mi.param_types.size()-2; i++)
				  mn=mn+mi.param_types.get(i)+", ";
			  if(mi.param_types.size()>=1)
				  mn=mn+mi.param_types.get(mi.param_types.size()-1)+")";
			  else
				  mn=mn+")";
			  int access_flags=mi.access_flags;
			  if((access_flags&0x0008)==0x0008)  //0x0008表示是否static
				  mn=mn+"  [static]";
			  Checkbox cb=new Checkbox(mn, cg, false);
			  cb.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
					if(l2.isEnabled()==false)
						l2.setEnabled(true);
					}	  
			  });
			  panel.add(cb);
		  }
		  sp.add(panel);
		  frame.revalidate();
	}
	
	private void refresh(){
		if(cur_file_path!=null){
			loadfile(cur_file_path);
		}
	}
	
	private void clear(){
		cur_file_path=null;
		sourcecode.setText("");
		console.setText("");
		sp.removeAll();
		l2.setEnabled(false);
		
		frame.revalidate();
	}
	
	public class ToolLabel extends Label{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private URL imgurl;
		
		public ToolLabel(URL url){
			setSize(25, 25);
			imgurl=url;
		}

		public void paint(Graphics g){
			Image i=new ImageIcon(imgurl).getImage();
			g.drawImage(i, 0, 0, 16, 16, null);
		}
	}

}
