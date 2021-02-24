package com.andreipall.art.services;

import org.springframework.data.domain.Page;
import com.andreipall.art.entities.User;

public interface UserService {
	public abstract void addUser(User user);
	public abstract Page<User> findPaginated(int pageNo, int pageSize);
	public abstract User findById(int id);
	public abstract void saveUser(User user);
	public abstract void deleteUser(User user);
}
