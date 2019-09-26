# DynamicWebProject

WEB관련
##### 1. Git 클론 >> New project wizard >> dynamic web project >> 프로젝트 이름은 폴더명( DynamicWebProject ) >> 프로젝트 우클릭 >> Configure >> Convert to Maven
<br/><br/>
##### 2. Lombok 세팅 >> 메이븐을 통해 다운로드 된 jar 파일로 이동 후 jar 실행 >> Specificy location >> 원하는 eclipse.exe 파일 찾은 후 Install / update >> 이클립스 재실행 (인식 잘 안되면 메이븐 업데이트)
<br/><br/>
##### 3. DB접속 암호화 환경변수 세팅
###### *** window : 환경변수값 추가 >> ASOG_ENCRYPTION_KEY : 복호화 키값
###### ***Linux   : 환경변수값 추가 >> vi /etc/init.d/tomcat >> export ASOG_ENCRYPTION_KEY=키값
<br/><br/>
###### 갑작스런 에러 : 3은(는) 3바이트 UTF-8 시퀀스에 대해 부적합한 바이트입니다. 혹은 Invalid byte 3 of 3-byte UTF-8 sequence 에러 발생시 
###### *** window : eclipse.ini >> -Dfile.encoding=UTF-8이거 추가한 상태 » 프로젝트 클린
###### ***Linux   : tomcat/bin/setenv.sh 생성 후 export JAVA_OPTS=”$JAVA_OPTS -Dfile.encoding=UTF-8” 이것 추가
<br/><br/>
##### 4. 배포 환경 구성 
##### {user}/.m2/settings-security.xml 하단 내용 추가
&lt;settingsSecurity> <br/>
    &lt;master>{masterKey}&lt;/master><br/>
&lt;/settingsSecurity>
<br/><br/>
##### {user}/.m2/settings.xml 여기 Profile부분에 속성값 추가<br/>
&lt;server><br/>
  &lt;id>gogoD&lt;/id><br/>
  &lt;username>my_username&lt;/username><br/>
  &lt;password>my_password&lt;/password><br/>
&lt;/server><br/>
<br/><br/>
&lt;profile><br/>
    &lt;id>deployD&lt;/id><br/>
    &lt;properties><br/>
        &lt;tomcat.deploy.url>url&lt;/tomcat.deploy.url><br/>
    &lt;/properties><br/>
&lt;/profile>
<br/><br/>
##### 5. 배포 
##### mvn clean tomcat7:deploy -PdeployD
##### mvn clean tomcat7:redeploy -PdeployD
(재배포시 - 재실행 필요없음)
