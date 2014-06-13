package com.db;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*;


/**
 * @author feeling
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DbConn
{
	private Connection conn;
	private Statement stmt;
	private String dsname;
	
	 
	public DbConn()
	{
		this.dsname = null;
	}
	
	public DbConn(String dsname) {
		this.dsname = dsname;		
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public int createConn()
	{
		try
		{
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = null;
			if (this.dsname==null)
			{
				ds = (DataSource)envContext.lookup("jdbc/filedb");
			}else{
				ds = (DataSource)envContext.lookup(this.dsname);
			}
			conn = ds.getConnection();
			stmt = conn.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Exception!");
			e.printStackTrace();
			return -2;
		}

		return 1;
	}

	public int closeConn()
	{
		try
		{
			if(this.conn!=null) this.conn.close();
		}
		catch(Exception e)
		{
			System.out.println("CloseException!");
			return -2;
		}
		return 1;
	}
	
	public Vector<Vector<String>> querySql(String sql)
	{
		if (this.stmt==null)
		{
			System.out.println("stmt is null");
			return null;
		}
		
		ResultSet rs = null;
		Vector<Vector<String>> vtrSelect = new Vector<Vector<String>>();

		try 
		{
			ResultSetMetaData rsmd = null;
			int numCols;
			Vector<String> tmpvct;

	    	rs      = this.stmt.executeQuery(sql);
	    	rsmd    = rs.getMetaData();
	    	numCols = rsmd.getColumnCount();
	    	
	    	while ( rs.next() ) 
			{
				tmpvct = new Vector<String>();
	       		for(int i = 1; i <= numCols; i ++) 
				{
					String tmpStr=rs.getString(i);
					tmpvct.addElement(tmpStr);
				}
				vtrSelect.addElement(tmpvct);
	    	} 
		} 
		catch(SQLException e) {
			e.printStackTrace();
			return null;
	  	}
	    	
	    rs = null;
	    return vtrSelect;
	}
	
	public int updateSql(String sql)
	{
		if (this.stmt==null)
		{
			System.out.println("stmt is null");
			return -1;
		}
		
		int i=0;
		try{
			this.conn.setAutoCommit(false);
			i = this.stmt.executeUpdate(sql);
			this.conn.commit();
		}catch (Exception e)
		{
			try{
				this.conn.rollback();
			}catch (Exception e2)
			{
				e2.printStackTrace();
				i=-1;
			}
			e.printStackTrace();
			i=-1;
		}
		
		return i;
	}
	
	public int[] updateBatchSql(String[] sqls)
	{
		if (this.stmt==null)
		{
			System.out.println("stmt is null");
			return null;
		}
		
		int[] i = null;
		
		try{
			for (int x=0;x<sqls.length;x++)
			{
				this.stmt.addBatch(sqls[x]);
			}
			
			this.conn.setAutoCommit(false);
			i = this.stmt.executeBatch();
			this.conn.commit();			
		}catch (Exception e)
		{
			try{
				this.conn.rollback();
			}catch (Exception e2)
			{
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return i;
	}
	
	public int[] updateBatchSql(List<String> lsql)
	{
		if (this.stmt==null)
		{
			System.out.println("stmt is null");
			return null;
		}
		
		if (lsql==null || lsql.size()<=0)
			return null;
		
		String[] sqls = new String[lsql.size()];
		for (int i=0;i<lsql.size();i++)
		{
			sqls[i] = lsql.get(i);
		}
		
		return updateBatchSql(sqls);
	}
	
	public static Vector<Vector<String>> toVector(ResultSet rs)
	{
		if (rs==null)
			return null;
		
		Vector<Vector<String>> vtrSelect = new Vector<Vector<String>>();

		try 
		{
			ResultSetMetaData rsmd = null;
			int numCols;
			Vector<String> tmpvct;

	    	rsmd    = rs.getMetaData();
	    	numCols = rsmd.getColumnCount();
	    	
	    	while ( rs.next() ) 
			{
				tmpvct = new Vector<String>();
	       		for(int i = 1; i <= numCols; i ++) 
				{
					String tmpStr=rs.getString(i);
					tmpvct.addElement(tmpStr);
				}
				vtrSelect.addElement(tmpvct);
	    	} 
		} 
		catch(SQLException e) {
			e.printStackTrace();
			return null;
	  	}
	    	
	    rs = null;
	    return vtrSelect;
	}
	
}

