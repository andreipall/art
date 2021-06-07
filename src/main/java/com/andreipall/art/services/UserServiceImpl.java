package com.andreipall.art.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.andreipall.art.entities.User;
import com.andreipall.art.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userRepository.save(user);
	}

	@Override
	public Page<User> findPaginated(int pageNo, int pageSize) {
		Sort sort = Sort.by("id").ascending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return this.userRepository.findAll(pageable);
	}

	@Override
	public User findById(int id) {
		return this.userRepository.getById(id);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userRepository.save(user);
	}

	@Override
	public void deleteUser(User user) {
		this.userRepository.deleteById(user.getId());
	}

}
