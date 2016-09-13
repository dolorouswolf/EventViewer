package com.cmb.ccd.viewer.model;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LM on 2016/9/12.
 */
public class Service extends BaseClass {
    private String repositoryName;
    private Pattern repositoryPattern = Pattern.compile("([a-z]|[A-Z])+(Repository|Dao)");


    public Service(List<String> context, String fileName) throws Exception {
        super(context, fileName);
        Optional<String> optional = context.stream().filter(x->x.contains("@Autowired"))
                .map(x-> {
                    Matcher matcher = repositoryPattern.matcher(x.replace("@Autowired ",""));
                    if (matcher.find()){
                        return matcher.group();
                    } else {
                        return "";
                    }
                }).filter(x-> x != "").findFirst();
        if (optional.isPresent()) {
            this.repositoryName = optional.get();
        }
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    @Override
    public boolean isValidate() {
        return super.isValidate() && (getRepositoryName() != null && getRepositoryName() != "");
    }

    @Override
    public String toString() {
        return "Service:"+super.toString() + ",repository["+getRepositoryName()+"]";
    }
}
