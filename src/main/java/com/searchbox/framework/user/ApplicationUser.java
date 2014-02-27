package com.searchbox.framework.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import com.searchbox.framework.domain.UserRole;

public class ApplicationUser extends SocialUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4791454719715028528L;

	private Long id;

	private String firstName;

	private String lastName;

	// private SocialMediaService socialSignInProvider;

	public ApplicationUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password,true,true, true, true, authorities);
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	// public SocialMediaService getSocialSignInProvider() {
	// return socialSignInProvider;
	// }

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id)
				.append("username", getUsername())
				.append("firstName", firstName).append("lastName", lastName)
				.append("roles", this.getAuthorities())
				// .append("socialSignInProvider", socialSignInProvider)
				.toString();
	}

	public static class Builder {

		private Long id;

		private String username;

		private String firstName;

		private String lastName;

		private String password;

		// private Role role;
		//
		// private SocialMediaService socialSignInProvider;

		private Set<GrantedAuthority> roles;

		public Builder() {
			this.roles = new HashSet<>();
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder password(String password) {
			if (password == null) {
				password = "SocialUser";
			}

			this.password = password;
			return this;
		}

		public Builder role(UserRole role) {

			this.roles.add(role);

			return this;
		}

		// public Builder socialSignInProvider(
		// SocialMediaService socialSignInProvider) {
		// this.socialSignInProvider = socialSignInProvider;
		// return this;
		// }

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public ApplicationUser build() {
			ApplicationUser user = new ApplicationUser(username, password,
					roles);

			user.id = id;
			user.firstName = firstName;
			user.lastName = lastName;
			// user.socialSignInProvider = socialSignInProvider;

			return user;
		}
	}
}
