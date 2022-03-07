package githubpeekbyamit.example.githubpeek;

public class RepoModel {
    private String name,stargazers_count,forks_count,language,html_url;

    public RepoModel() {

    }

    public RepoModel(String name, String stargazers_count, String forks_count,String language,String html_url) {
        this.name = name;
        this.stargazers_count = stargazers_count;
        this.forks_count = forks_count;
        this.language = language;
        this.html_url = html_url;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(String stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getForks_count() {
        return forks_count;
    }

    public void setForks_count(String forks_count) {
        this.forks_count = forks_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}