
package com.l1j5.web.example.service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.l1j5.web.example.model.dao.BbsDAO;
import com.l1j5.web.example.model.dao.CommentDAO;
import com.l1j5.web.example.model.dto.Paging;
import com.l1j5.web.example.model.dto.Post;
import com.l1j5.web.example.model.dto.PostParam;

@Service
public class BbsService {

	@Autowired
	private BbsDAO bbsDAO;
	
	@Autowired
	private CommentDAO commentDAO;	

	@Value("#{l1j5Prop['file.upload.dir']}")
	private String fileDir;

	public JSONObject getPostList(PostParam param) {
		JSONObject json = new JSONObject();
		List<Post> postList = null;
		
		int count = bbsDAO.getCountPostList(param);
		Paging paging = new Paging(param.getCurrentPageNo(), count);
		param.setFromRowNum(paging.getFromRowNum());
		param.setToRowNum(paging.getToRowNum());
		
		if(count > 0){
			postList = bbsDAO.getPostList(param);
		}
		
		json.accumulate("paging", paging);
		json.accumulate("postList", postList);
		
		return json;
	}

	public Post getPost(String b_id) {
		return bbsDAO.getPost(b_id);
	}

	@Transactional
	public int deletePost(PostParam postParam) {
		int result = bbsDAO.deletePost(postParam);
		if(result > 0) {
			commentDAO.deleteCommentByBbsNum(postParam.getB_id());
		}

		return bbsDAO.deletePost(postParam);
	}

	public int updatePost(PostParam postParam) {
		return bbsDAO.updatePost(postParam);
	}

	@Transactional
	public int insertPost(PostParam postParam) throws IOException {

		String name = postParam.getAttachment().getOriginalFilename();
		boolean hasFile = name != null && !"".equals(name);
		int result = -1;
		
		System.out.println(hasFile);

		Post post = new Post();
		if (hasFile) {
			String fileName = null;
			if (postParam.getTitle().length() < 5) {
				fileName = postParam.getTitle();
			} else {
				fileName = postParam.getTitle().substring(0, 5);
			}
			post.setFile_name(fileName);
			post.setFile_path(postParam.getAttachment().getOriginalFilename());
			result = bbsDAO.insertAttachFile(post);			
		}
		// 결과값으로 f_id가 온다.
		if (result > 0 || result == -1) {
			post.setTitle(postParam.getTitle());
			post.setB_content(postParam.getContent());
			post.setU_id(postParam.getLoginUserId());
			result = bbsDAO.insertPost(post);
		}

		if (result > 0 && hasFile) {
			FileOutputStream fos = new FileOutputStream(fileDir + name);
			InputStream is = postParam.getAttachment().getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[2048];
			while (bis.read(buffer) != -1) {
				fos.write(buffer);
			}
			fos.close();
		}
		return result;
	}
}
