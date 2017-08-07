package com.logicmonitor.miniurl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.logicmonitor.miniurl.linkinfo.LinkInfo;

public class MiniURLDao {

	private DBConnectionManager connectionManager=null;
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private static final String dbUrl = "jdbc:mysql://localhost:3306/miniurl";
	private static final String dbUser = "root"; 
	private static final String dbPassword = "root";
	
	public MiniURLDao() {
		// TODO Auto-generated constructor stub
		try {
			connectionManager = new DBConnectionManager(dbUrl, dbUser, dbPassword);
			connection = connectionManager.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int create(LinkInfo linkInfo) throws SQLException{
		String insert = "INSERT INTO linkinfo (shortUrl, longUrl, isBlackListed) VALUES (?,?,?)";
		preparedStatement = connection.prepareStatement(insert);
		preparedStatement.setString(1, linkInfo.getShortUrl());
		preparedStatement.setString(2, linkInfo.getLongUrl());
		preparedStatement.setBoolean(3, false);
		int rowCount = preparedStatement.executeUpdate();
		preparedStatement.close();
		return rowCount;
	}
	
	
	public LinkInfo getLongUrl(String url) throws SQLException{
		String selectSQL = "SELECT * FROM linkinfo WHERE longUrl = ?";
		preparedStatement = connection.prepareStatement(selectSQL);
		preparedStatement.setString(1, url);
		ResultSet rs = preparedStatement.executeQuery();
		LinkInfo linkInfo = null;
		while (rs.next()) {
			linkInfo = new LinkInfo();
			linkInfo.setLongUrl(rs.getString("longUrl"));
			linkInfo.setShortUrl(rs.getString("shortUrl"));
			linkInfo.setBlackListed(rs.getBoolean("isBlackListed"));
		}
		preparedStatement.close();
		return linkInfo;
	}
	
	public LinkInfo getShortUrl(String url) throws SQLException{
		String selectSQL = "SELECT * FROM linkinfo WHERE shortUrl = ?";
		preparedStatement = connection.prepareStatement(selectSQL);
		preparedStatement.setString(1, url);
		ResultSet rs = preparedStatement.executeQuery();
		LinkInfo linkInfo = null;
		while (rs.next()) {
			linkInfo = new LinkInfo();
			linkInfo.setLongUrl(rs.getString("longUrl"));
			linkInfo.setShortUrl(rs.getString("shortUrl"));
			linkInfo.setBlackListed(rs.getBoolean("isBlackListed"));
		}
		preparedStatement.close();
		return linkInfo;
	}
	
	public int update(String shortUrl, boolean isBlackListed) throws SQLException{
		String updateSQL = "Update linkinfo set isBlackListed = ? where shortUrl = ?";
		preparedStatement = connection.prepareStatement(updateSQL);
		preparedStatement.setBoolean(1, isBlackListed);
		preparedStatement.setString(2, shortUrl);
		int rowCount = preparedStatement.executeUpdate();
		preparedStatement.close();
		return rowCount;
	}
	
	public void closeConnection() throws SQLException{
		connection.close();
	}
	
	public String returnLastInsertedID() throws SQLException {
		String selectSQL = "select shortUrl from linkinfo order by creation_timestamp  desc limit 1";
		preparedStatement = connection.prepareStatement(selectSQL);
		ResultSet rs = preparedStatement.executeQuery();
		String s = null;
		while(rs.next()){
			s= rs.getString(1);
			break;
		}
		preparedStatement.executeQuery();
		return s;
	}
}
