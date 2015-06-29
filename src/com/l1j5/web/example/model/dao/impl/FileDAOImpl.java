package com.l1j5.web.example.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.l1j5.web.example.model.dao.FileDAO;
import com.l1j5.web.example.model.dto.FileInfo;

@Repository
public class FileDAOImpl extends JdbcDaoSupport implements FileDAO {
	
	@Autowired
	public FileDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public FileInfo getFileInfo(String fid) {
		

			StringBuffer sql = new StringBuffer();
			sql.append(" select f_id as fid");
			sql.append(" ,file_name as fileName");
			sql.append(" ,file_path as filePath");
			sql.append(" from file_info");
			sql.append(" where f_id= ? ");

			
			return getJdbcTemplate().queryForObject(sql.toString(),
					new Object[] {fid},
					new RowMapper<FileInfo>(){
					
						@Override
						public FileInfo mapRow(ResultSet rs, int arg1) throws SQLException{
							FileInfo fileInfo = new FileInfo();
							
							fileInfo.setFid(rs.getString("fid"));
							fileInfo.setFileName(rs.getString("fileName"));
							fileInfo.setFilePath(rs.getString("filePath"));
							
							return fileInfo;
				
					
						}
			});

	}
}
