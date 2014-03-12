package com.searchbox.collection.oppfin;

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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.lf5.util.DateFormatManager;
import org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile;
import org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;
import com.searchbox.framework.config.RootConfiguration;

import eu.europa.ec.een.tools.services.soap.IPODServiceSOAPProxy;

@Configurable
@Component
public class EENCollection extends AbstractBatchCollection implements
        SynchronizedCollection {

    /**
	 * 
	 */

    /** Properties */
    private final static String CRAWLER_USER_AGENT = "crawler.userAgent";
    private final static String EEN_SERVICE_URL = "een.service.url";
    private final static String EEN_SERVICE_USER = "een.service.user";
    private final static String EEN_SERVICE_PWD = "een.service.password";

    private final static String CRAWLER_USER_AGENT_DEFAULT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.73.11 (KHTML, like Gecko) Version/7.0.1 Safari/537.73.11";
    private final static String EEN_SERVICE_URL_DEFAULT = "http://een.ec.europa.eu/tools/services/podv6/QueryService.svc/GetProfiles?da=START&db=END&u=USER&p=PASSWORD";
    private final static String EEN_SERVICE_USER_DEFAULT = "CH00261";
    private final static String EEN_SERVICE_PWD_DEFAULT = "5b6f81dc4e04246da13cd9e4d93fad66";

    @Resource
    private Environment env;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(EENCollection.class);

    public static List<Field> GET_FIELDS() {
        List<Field> fields = new ArrayList<Field>();
        fields.add(new Field(String.class, "id"));
        fields.add(new Field(String.class, "docSource"));
        fields.add(new Field(String.class, "docType"));
        fields.add(new Field(String.class, "programme"));

        fields.add(new Field(String.class, "eenContactEmail"));
        fields.add(new Field(String.class, "eenContactFullname"));
        fields.add(new Field(String.class, "eenContactOrganization"));
        fields.add(new Field(String.class, "eenContactOrganizationcountry"));
        fields.add(new Field(String.class, "eenContactPhone"));

        fields.add(new Field(Date.class, "eenDatumSubmit"));
        fields.add(new Field(String.class, "eenContactOrganization"));
        fields.add(new Field(String.class, "eenPartnerships"));
        fields.add(new Field(String.class, "eenContentSummary"));
        fields.add(new Field(String.class, "eenReferenceExternal"));
        fields.add(new Field(String.class, "eenContactConsortium"));
        fields.add(new Field(String.class, "eenKeywordNacesKey"));
        fields.add(new Field(String.class, "eenCompanyCountryLabel"));
        fields.add(new Field(String.class, "eenCompanyCertificationsOther"));
        fields.add(new Field(String.class, "eenEoiStatus"));
        fields.add(new Field(String.class, "eenContactPartnerid"));
        fields.add(new Field(String.class, "eenReferenceType"));
        fields.add(new Field(String.class, "eenContentTitle"));
        fields.add(new Field(String.class, "eenCompanyLanguagesLabel"));
        fields.add(new Field(String.class, "eenDisseminationPreferredLabel"));
        fields.add(new Field(String.class, "eenCompanyTransnational"));
        fields.add(new Field(String.class, "eenCompanyCountryKey"));
        fields.add(new Field(String.class, "eenContentDescription"));
        fields.add(new Field(String.class, "eenKeywordTechnologiesLabel"));

        fields.add(new Field(Date.class, "eenDatumDeadline"));
        fields.add(new Field(Date.class, "eenDatumUpdate"));
        fields.add(new Field(String.class, "eenCompanyKind"));
        fields.add(new Field(String.class, "eenCompanyExperience"));
        fields.add(new Field(String.class, "eenKeywordNacesLabel"));
        fields.add(new Field(String.class, "eenCompanyTurnover"));
        fields.add(new Field(String.class, "eenDisseminationPreferredKey"));
        fields.add(new Field(Integer.class, "eenCompanySince"));
        return fields;
    }

    public EENCollection() {
        super("EENCollection");
    }

    public EENCollection(String name) {
        super(name);
    }

    public ItemReader<Profile> reader() throws RemoteException {
        return new ItemReader<Profile>() {

            IPODServiceSOAPProxy eenService;
            ProfileQueryRequest request;
            List<Profile> profiles;
            Date date = new Date(System.currentTimeMillis());
            Date start = new Date(System.currentTimeMillis());
            DateFormat dfmt = new DateFormatManager("YYYYMMdd")
                    .getDateFormatInstance();

            {
                // Get the service
                eenService = new IPODServiceSOAPProxy();
                // env.getProperty(EEN_SERVICE_URL, EEN_SERVICE_URL_DEFAULT));

                // Set the password
                request = new ProfileQueryRequest();
                request.setUsername(EEN_SERVICE_USER_DEFAULT);
                request.setPassword(EEN_SERVICE_PWD_DEFAULT);
                // String[] profileTypes = {"TO"};
                // request.setProfileTypes(profileTypes);

                // Start with an empty list of profiles
                profiles = new ArrayList<Profile>();

                date = start;
            }

            int i = 0;

            public Profile read() throws RemoteException {
                if (start.after(DateUtils.addYears(date, 3))) {
                    return null;
                }
//                if ((i++) > 200)
//                    return null;
                if (profiles.isEmpty()) {
                    Date end = DateUtils.addDays(start, 5);
                    EENCollection.LOGGER.info("Fetching EEN from "
                            + dfmt.format(start) + " to " + dfmt.format(end));
                    // time to get some new profiles...
                    request.setDeadlineDateAfter(dfmt.format(start));
                    request.setDeadlineDateBefore(dfmt.format(end));
                    try {
                        Profile[] newProfiles = eenService.getProfiles(request);
                        EENCollection.LOGGER.info("adding: "
                                + newProfiles.length + " new profiles");
                        profiles.addAll(Arrays.asList(newProfiles));
                    } catch (Exception e) {
                        LOGGER.error("Could not load profiles: " + e);
                    }
                    start = end;
                }
                return profiles.remove(0);
            }
        };

    }

    private static void getFieldValues(Object target, String path,
            FieldMap fields) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, IntrospectionException {
        // LOGGER.info("Checking object: ([] -> " + target.getClass().isArray()
        // + ") of type: " + target.getClass().getSimpleName()
        // + " path: " + path
        // + " value: " + target.toString());
        if (Date.class.isAssignableFrom(target.getClass())) {
            fields.put(path, target);
        } else if (Calendar.class.isAssignableFrom(target.getClass())) {
            fields.put(path, ((Calendar) target).getTime());
        } else if (!target.getClass().isArray()
                && target.getClass().getName().startsWith("java.")) {
            if (!target.toString().isEmpty()) {
                fields.put(path, target.toString());
            }
        } else {
            for (java.lang.reflect.Field field : target.getClass()
                    .getDeclaredFields()) {
                if (field.getName().startsWith("_")
                        || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                // LOGGER.info("Checking field: ([] -> " +
                // field.getType().isArray() + ") " + field.getName() +
                // " of type: " + field.getType().getSimpleName());
                field.setAccessible(true);
                Method reader = new PropertyDescriptor(field.getName(),
                        target.getClass()).getReadMethod();
                try {
                    if (reader != null) {
                        Object obj = reader.invoke(target);
                        if (field.getType().isArray()) {
                            for (Object object : (Object[]) obj) {
                                getFieldValues(
                                        object,
                                        path
                                                + WordUtils.capitalize(field
                                                        .getName()
                                                        .toLowerCase()), fields);
                            }
                        } else if (java.util.Collection.class
                                .isAssignableFrom(obj.getClass())) {
                            for (Object object : (java.util.Collection) obj) {
                                getFieldValues(
                                        object,
                                        path
                                                + WordUtils.capitalize(field
                                                        .getName()
                                                        .toLowerCase()), fields);
                            }
                        } else {
                            getFieldValues(
                                    obj,
                                    path
                                            + WordUtils.capitalize(field
                                                    .getName().toLowerCase()),
                                    fields);
                        }
                    }
                } catch (Exception e) {
                    ;
                }

            }
        }
    }

    public ItemProcessor<Profile, FieldMap> itemProcessor() {
        return new ItemProcessor<Profile, FieldMap>() {

            public FieldMap process(Profile item) throws IOException {

                FieldMap doc = new FieldMap();

                try {

                    getFieldValues(item, "een", doc);
                    // for (Entry<String, List<Object>> field : doc.entrySet())
                    // {
                    // for (Object value : field.getValue()) {
                    // LOGGER.info(field.getKey() + " -- " + value);
                    // }
                    // }
                    doc.put("docSource", "EEN");
                    doc.put("docSource", "EEN");
                    doc.put("docType", "collaboration");
                    doc.put("id", item.getReference().getExternal());
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IntrospectionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return doc;
            }
        };
    }

    public ItemWriter<FieldMap> writer() {
        ItemWriter<FieldMap> writer = new ItemWriter<FieldMap>() {

            public void write(List<? extends FieldMap> items) {
                for (FieldMap fields : items) {
                    Map<String, Object> actualFields = new HashMap<String, Object>();
                    for (Entry<String, List<Object>> field : fields.entrySet()) {
                        actualFields.put(field.getKey(), (field.getValue()
                                .size() > 1) ? field.getValue() : field
                                .getValue().get(0));
                    }
                    try {
                        getSearchEngine().indexMap(getName(), actualFields);
                    } catch (Exception e) {
                        LOGGER.error("Could not index document",e);
                    }
                }
            }
        };
        return writer;
    }

    @Override
    protected FlowJobBuilder getJobFlow(JobBuilder builder) {
        try {
            Step step = stepBuilderFactory.get("getFile")
                    .<Profile, FieldMap> chunk(5).reader(reader())
                    .processor(itemProcessor()).writer(writer()).build();

            return builder.flow(step).end();
        } catch (Exception e) {
            LOGGER.error("Could not create flow for collection: " + getName());
        }
        return null;
    }

    public static void main(String... args)
            throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                RootConfiguration.class);

        EENCollection collection = context.getBean(EENCollection.class);
        collection.synchronize();
    }
}
