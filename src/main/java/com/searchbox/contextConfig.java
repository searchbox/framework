package com.searchbox;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.searchbox.app.domain.PresetDefinition;
import com.searchbox.app.domain.SearchElementDefinition;
import com.searchbox.app.domain.Searchbox;
import com.searchbox.app.repository.SearchboxRepository;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.searchbox.core","com.searchbox.ref",
		"com.searchbox.app","com.searchbox.service", "com.searchbox.data"})
public class ApplicationConfig  {
	
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
        		.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
				.addScript("classpath:org/springframework/batch/core/schema-h2.sql")
				.setType(EmbeddedDatabaseType.H2)
				.build();
    }

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("com.searchbox.app");
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        return hibernateJpaVendorAdapter;
    }

    @Bean(name="transactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }


    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        SearchboxRepository repository = context.getBean(SearchboxRepository.class);


        // fetch all searchbox
        Iterable<Searchbox> searchboxes = repository.findAll();
        System.out.println("Searchbox found with findAll():");
        System.out.println("-------------------------------");
        for (Searchbox searchbox : searchboxes) {
        	for(PresetDefinition pdef:searchbox.getPresets()){
        		System.out.println("Preset is: " + pdef.getSlug());
        		for(SearchElementDefinition edef:pdef.getSearchElements()){
            		System.out.println("\tSearchElementDef is: " + edef.getPosition());
//            		SearchElement element = edef.toElement();
//            		System.out.println("\t++ SearchElement is: " + element.getLabel());

        		}
        		
        	}
        }
        System.out.println();

        context.close();
    }

}