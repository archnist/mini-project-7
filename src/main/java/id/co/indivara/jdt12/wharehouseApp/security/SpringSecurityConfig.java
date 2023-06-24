package id.co.indivara.jdt12.wharehouseApp.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("whuser").password("{noop}warehouse").roles("WHUSER")
                .and()
                .withUser("supplier").password("{noop}supp").roles("SUPPLIER")
                .and()
                .withUser("admin").password("{noop}admin").roles("ADMIN", "WHUSER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/warehouse/register").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/store/register").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/warehouse/find/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/transaction/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/transaction/{type}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/transaction/suppToWhouse").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/transaction/whouseToWhouse").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/transaction/whouseToStore").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/goods/show/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/goods/show/{goodsId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/store/show/all").hasRole("WHUSER")
                .antMatchers(HttpMethod.GET, "/store/show/{storeName}").hasRole("WHUSER")
                .antMatchers(HttpMethod.GET, "/warehouse/show/all").hasRole("WHUSER")
                .antMatchers(HttpMethod.GET, "/warehouse/show/{warehouseName}").hasRole("WHUSER")
                .antMatchers(HttpMethod.GET, "/warehouse/find/{warehouse}").hasRole("WHUSER")
                .antMatchers(HttpMethod.GET, "/warehouse/transaction/suppToWarehouse/{warehouse}").hasRole("WHUSER")
                .antMatchers(HttpMethod.GET, "/warehouse/transaction/warehouseToWarehouse/{warehouse}").hasRole("WHUSER")
                .antMatchers(HttpMethod.GET, "/warehouse/transaction/warehouseToStore/{warehouse}").hasRole("WHUSER")
                .antMatchers(HttpMethod.GET, "/warehouse/transaction/{warehouse}").hasRole("WHUSER")
                .antMatchers(HttpMethod.POST, "/supp/add/{whouseId}/{goodsId}").hasRole("SUPPLIER")
                .antMatchers(HttpMethod.POST, "/warehouse/transfer/{goodsId}/{source}/to/{destination}").hasRole("WHUSER")
                .antMatchers(HttpMethod.POST, "/warehouse/delivery/{goodsId}/{whouseSrc}/to/{storeDest}").hasRole("WHUSER")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
