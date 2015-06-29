package com.l1j5.web.example.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.l1j5.web.example.model.dao.FileDAO;
import com.l1j5.web.example.model.dto.FileInfo;

@Controller
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileDAO fileDao;
	
	@Value("#{l1j5Prop['file.upload.dir']}")
	private String fileDir;
	
	@RequestMapping("/download/{fid}")
	public void download(@PathVariable String fid, HttpServletResponse response){
		

		FileInfo fileInfo = fileDao.getFileInfo(fid);
		
		File file = new File(fileDir + fileInfo.getFilePath());
		FileInputStream fis =null;
		try {
			fis = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			String fileName = URLEncoder.encode(fileInfo.getFileName(), "utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			
			byte[] b = new byte[1024];
			while(fis.read(b) != -1){
				os.write(b);
			}
			os.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(fis != null) try{ fis.close();} catch(IOException e){}
		}
		
	}
}
