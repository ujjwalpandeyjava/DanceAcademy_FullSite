# Spring Security Top Stuff
Spring maintains the session automatically with the help of JSessionID.


## @EnableWebSecurity
Enables spring filter chains...

	Just to see
	Security filter chain: [
	  DisableEncodeUrlFilter
	  WebAsyncManagerIntegrationFilter
	  SecurityContextHolderFilter
	  HeaderWriterFilter
	  CorsFilter
	  CsrfFilter
	  LogoutFilter
	  UsernamePasswordAuthenticationFilter
	  DefaultLoginPageGeneratingFilter
	  DefaultLogoutPageGeneratingFilter
	  BasicAuthenticationFilter
	  RequestCacheAwareFilter
	  SecurityContextHolderAwareRequestFilter
	  AnonymousAuthenticationFilter
	  ExceptionTranslationFilter
	  AuthorizationFilter
	]

- By default it creates a bean: Filter springSecurityFilterChain(); same is name of bean

## UserDetails (Manage all user related information interface)
class and interface...
	
	1. UserName
	2. Password
	3. Roles/Authorities
		- Visitor
		- User
		- Customer
	4. isAccountActive
	5. 
	
## User (implementation of UserDetails)
It is the implementation of interface UserDetails.

We use the User to create the user with details or we can extend UserDetils and implement our own.



## GrantedAuthority (Interface, use class SimpleGrantedAuthority)
Provides the roles/authorities.


## UserDetailsManager (Interface, use to do CRUD, Service related work, )
Used to do CRUD

It is the service provided for the UserDetails

We can directly use it or implement the methods.

By default spring provide use a "inMemoryUserDetailsManager"/JDBCUserDetailsManager for HardCoded users.