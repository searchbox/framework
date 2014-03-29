SELECT 
 
##Assignee
assignee.id as 'assignee.id',
assignee.login as 'assignee.login',
assignee.name as 'assignee.name',
 
##Reporter
reporter.id as 'reporter.id',
reporter.login as 'reporter.login',
reporter.name as 'reporter.name',
 
##Author
issue.author_login as 'author.login',
 
##Roule Definition
rule.id as 'rule.id',
rule.name as 'rule.name',
rule.status as 'rule.status',
rule.description as 'rule.description',
rule.language as 'rule.language',
rule.plugin_name as 'rule.plugin_name',
rule.priority as 'rule.priority',
rule.created_at as 'rule.creation_date',
rule.updated_at as 'rule.updated_at',
 
## Component Fields
project.id as 'root.id',
component.name as 'component.name', 
component.id as 'component.id', 
component.path as 'component.path', 
 
## Issue Fields
issue.id as id, issue.effort_to_fix as effort_to_fix, issue.status as status, issue.resolution
as resolution, issue.issue_creation_date as creation_date, issue.issue_close_date as close_date, 
issue.issue_update_date as update_date, issue.severity as severity, issue.line as line, issue.technical_debt
as technical_debt
 
FROM sonar.issues AS issue
    LEFT JOIN sonar.projects AS project ON issue.root_component_id = project.id
    LEFT JOIN sonar.projects AS component ON issue.component_id = component.id
    LEFT JOIN sonar.rules AS rule ON issue.rule_id = rule.id
    LEFT JOIN sonar.users AS reporter ON issue.reporter = reporter.id
    LEFT JOIN sonar.users AS assignee ON issue.assignee = assignee.id