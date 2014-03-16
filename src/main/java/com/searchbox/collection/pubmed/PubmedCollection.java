/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.collection.pubmed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;

@Configurable
public class PubmedCollection extends AbstractBatchCollection implements
    SynchronizedCollection {

  @Autowired
  ApplicationContext context;

  @Autowired
  StepBuilderFactory stepBuilderFactory;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(PubmedCollection.class);

  public static List<Field> GET_FIELDS() {
    List<Field> fields = new ArrayList<Field>();
    fields.add(new Field(String.class, "id"));
    fields.add(new Field(Date.class, "article-creation-date"));
    fields.add(new Field(Integer.class, "article-year"));
    fields.add(new Field(Date.class, "article-completion-date"));
    fields.add(new Field(Date.class, "article-revision-date"));
    fields.add(new Field(String.class, "article-pubmodel"));
    fields.add(new Field(String.class, "journal-title"));
    fields.add(new Field(String.class, "journal-title-abrv"));
    fields.add(new Field(String.class, "article-title"));
    fields.add(new Field(String.class, "article-abstract"));
    fields.add(new Field(String.class, "author"));
    fields.add(new Field(String.class, "article-lang"));
    fields.add(new Field(String.class, "publication-type"));
    fields.add(new Field(String.class, "substance-name"));
    fields.add(new Field(String.class, "article-descriptor"));
    fields.add(new Field(String.class, "article-qualifier"));
    return fields;
  }

  public PubmedCollection() {
  }

  public PubmedCollection(String name) {
    super(name);
  }

  public ItemReader<Resource> reader() {
    return new ItemReader<Resource>() {
      boolean hasmore = true;

      @Override
      public Resource read() {
        if (hasmore) {
          hasmore = false;
          Resource resource = context
              .getResource("classpath:data/pubmedIndex.xml");
          if (resource.exists()) {
            LOGGER.info("Read has created this resource: "
                + resource.getFilename());
            return resource;
          }
        }
        return null;
      }
    };
  }

  public ItemProcessor<Resource, File> itemProcessor() {
    return new ItemProcessor<Resource, File>() {
      @Override
      public File process(Resource item) throws IOException {
        LOGGER.info("Processing stuff here...");
        return item.getFile();
      }
    };
  }

  @Override
  protected FlowJobBuilder getJobFlow(JobBuilder builder) {

    Step step = stepBuilderFactory.get("getFile").<Resource, File> chunk(1)
        .reader(reader()).processor(itemProcessor()).writer(fileWriter())
        .build();

    return builder.flow(step).end();

  }
}
