package com.learningwithsachin.taskmanagement.dto;


import com.learningwithsachin.taskmanagement.model.User;

public class UserDTO {


	private String firstName;
	private String profileImage;

	public String getFirstName () {
		return firstName;
	}

	public void setFirstName (String firstName) {
		this.firstName = firstName;
	}

	public String getProfileImage () {
		return profileImage;
	}

	public void setProfileImage (String profileImage) {
		this.profileImage = profileImage;
	}

	public static UserDTO fromUser(User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(user.getFirstName());
		userDTO.setProfileImage(user.getProfileImage());
		return userDTO;
	}
}
