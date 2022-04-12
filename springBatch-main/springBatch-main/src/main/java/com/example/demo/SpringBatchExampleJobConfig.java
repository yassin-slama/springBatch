package com.example.demo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.List;
@Slf4j
@Configuration
@EnableBatchProcessing

public class  SpringBatchExampleJobConfig {

	@Autowired
	private studentRepo studentRepo;
	@Autowired
    private   StepBuilderFactory stepBuilderFactory;
	@Autowired
	private  JobBuilderFactory jobBuilderFactory;



	@Bean
    public ItemReader<StudentDTO> itemReader() {
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(StudentDTO.class);

        return new StaxEventItemReaderBuilder<StudentDTO>()
                .name("studentReader")
                .resource(new ClassPathResource("data/students.xml"))
                .addFragmentRootElements("student")
                .unmarshaller(studentMarshaller)
                .build();
    }
    	@Bean // sans ca l'objet ne sera pas instancié au demarrage
	public Job bankJob() {
		Step step1 = stepBuilderFactory.get("step-load-data")
				    .<StudentDTO,StudentDTO>chunk(1) // il faut absolument specifier le diamant <BankTransaction,BankTransaction>
				    .reader(itemReader())
				.writer(new ItemWriter<StudentDTO>() {
						@Override
						public void write(List<? extends StudentDTO> list) throws Exception {
							studentRepo.saveAll(list);
							for (int i=0;i<list.size();i++){
								log.info(list.get(i).toString());

							}
						}
					})
				    .build();

		return jobBuilderFactory.get("bank-data-loader-job")
				.start(step1).build();

	}
}