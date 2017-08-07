package com.logicmonitor.miniurl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.logicmonitor.miniurl.dao.MiniURLDao;
import com.logicmonitor.miniurl.linkinfo.LinkInfo;
import com.logicmonitor.miniurl.util.EncodeDecodeURL;

/**
 * Servlet implementation class GenerateURL
 */
public class GenerateURL extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static long counter = -1;

	private static final String regex = "^[0-9a-zA-Z]*$";
	
	private static final String shortUrlPrefix = "http://www.urlshortener.com/";

	private MiniURLDao miniUrlDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateURL() {
		super();

		// TODO Auto-generated constructor stub
	}

	public void init(){
		miniUrlDao = new MiniURLDao();
		try {
			String lastInsertedID = miniUrlDao.returnLastInsertedID();
			miniUrlDao.closeConnection();
			if(lastInsertedID == null){
				counter = -1;
			}else{
				counter = EncodeDecodeURL.convertShortToLongURL(lastInsertedID);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String shortUrl = request.getParameter("shortUrl");
		try{
			/*Server side validation to check whether the input url is a valid URL*/
			if(shortUrl == null || !shortUrl.startsWith(shortUrlPrefix)) {
				throw new MalformedURLException();
			}
			shortUrl = shortUrl.substring(shortUrlPrefix.length());
			if(shortUrl == null ||  !shortUrl.matches(regex) || shortUrl.length()!=6){
				throw new MalformedURLException();
			}
			miniUrlDao = new MiniURLDao();
			LinkInfo linkInfo = miniUrlDao.getShortUrl(shortUrl);
			miniUrlDao.closeConnection();
			/*Return error if the short url is not present in the database*/
			if(linkInfo == null){
				response.sendError(HttpServletResponse.SC_NOT_FOUND,"URL is not present in the DB!");
			}
			/*Return error if the short url is blacklisted*/
			else if(linkInfo.isBlackListed()){
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "URL is blacklisted!");
			}else {
				Gson gson = new Gson();
				String json = gson.toJson(linkInfo);
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json");
				response.getWriter().write(json);
			}
			
		}catch(MalformedURLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String longUrl = request.getParameter("longUrl");
		longUrl = longUrl.trim();
		try {
			/* Check if the url in the input is valid.*/
			if(longUrl == null || longUrl.length()>2083){
				throw new MalformedURLException();
			}
			URL url = new URL(longUrl);

			/* Check if there is an already existing entry in the DB.*/
			miniUrlDao = new MiniURLDao();
			LinkInfo linkInfo = miniUrlDao.getLongUrl(longUrl);

			/*If the URL already exists, check whether it is blacklisted.*/
			
			if (linkInfo != null) {
				if(linkInfo.isBlackListed()){
					response.sendError(HttpServletResponse.SC_FORBIDDEN,"URL is blacklisted!");
				}else{
					response.setStatus(HttpServletResponse.SC_CONFLICT);
				}
			} else {
				/* If URL is not present in the DB then create a new URL*/
				linkInfo = new LinkInfo();
				linkInfo.setLongUrl(longUrl);
				long count = counter;
				
				/*Synchronized block is used so that no 2 threads can read the same value of the counter and 
				create a same shortURL*/
				
				synchronized (this) {
					counter++;
					count = counter;
				}
				String shortUrl = EncodeDecodeURL.createShortURL(count);
				linkInfo.setShortUrl(shortUrl);
				linkInfo.setBlackListed(false);
				miniUrlDao.create(linkInfo);
				miniUrlDao.closeConnection();
				response.setStatus(HttpServletResponse.SC_OK);
			}
			
			Gson gson = new Gson();
			String json = gson.toJson(linkInfo);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json");
			response.getWriter().write(json);
			
		} catch (MalformedURLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String shortUrl = request.getParameter("shortUrl");
		try{
			if(shortUrl == null || !shortUrl.startsWith(shortUrlPrefix)) {
				throw new MalformedURLException();
			}
			shortUrl = shortUrl.substring(shortUrlPrefix.length());
			if(shortUrl == null ||!shortUrl.matches(regex)|| shortUrl.length()!=6 ){
				throw new MalformedURLException();
			}
			miniUrlDao = new MiniURLDao();
			LinkInfo linkInfo = miniUrlDao.getShortUrl(shortUrl);
			if(linkInfo == null){
				response.sendError(HttpServletResponse.SC_NOT_FOUND,"URL is not present in the DB!");
			}else if(linkInfo.isBlackListed()){
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}else{
				miniUrlDao.update(shortUrl, true);
				miniUrlDao.closeConnection();
				response.setStatus(HttpServletResponse.SC_OK);
			}
			
		}catch(MalformedURLException e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
