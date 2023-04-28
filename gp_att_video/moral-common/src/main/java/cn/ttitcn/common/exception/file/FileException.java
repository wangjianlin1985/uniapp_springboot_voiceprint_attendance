package cn.ttitcn.common.exception.file;

import cn.ttitcn.common.exception.BaseException;

public class FileException extends BaseException {
	
	private static final long serialVersionUID = 1L;

	public FileException(String code, Object[] args) {
		super("file", code, args, null);
	}

}
