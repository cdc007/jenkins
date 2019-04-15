import hudson.model.*;


pipeline{ 
	
	agent any
	stages{
		stage("Hello Pipeline") {
			steps {
			    script {
					println "Hello Pipeline!"
					println env.JOB_NAME
					println env.BUILD_NUMBER
				}
			}
		}
		
		stage("Init paramters in json") {
			steps {
			    script {
					println "read josn input file"
					json_file = INPUT_JSON? INPUT_JSON.trim() : ""
					prop = readJSON file : json_file
					name = prop.NAME? prop.NAME.trim() : ""
					println "Name:" + name
					age = prop.AGE? prop.AGE.trim() : ""
					println "Age:" + age
					phone = prop.PHONE_NUMBER? prop.PHONE_NUMBER.trim() : ""
					println "Phone:" + phone
					address = prop.ADDRESS? prop.ADDRESS.trim() : ""
					println "Address:" + address
					email = prop.EMAIL? prop.EMAIL.trim() : ""
					println "Email:" + email
					gender = prop.GENDER? prop.GENDER.trim() : ""
					println "Gender:" + gender
					is_marry = prop.IS_MARRY? prop.IS_MARRY : false
					println "is_marry:" + is_marry
					is_smoke = prop.SMOKE? prop.SMOKE : false
					println "is_smoke:" + is_smoke
				}
			}
		}
		stage("call a method") {
			steps {
			    script {
					println "send the parameter as map type"
					model_call = load env.WORKSPACE + "/groovy/projectA-model.groovy"
					model_call.getUserInfo(name:name, age:age, phone:phone, address:address, email:email, gender:gender, is_marry:is_marry)
				}
			}
		}
		stage("check serive up") {
		    when {
		        expression {
		            return (is_smoke == true)
		        }
		    }
		    steps {
			    script {
					println "SMOKE TEST: check service startup"
				}
			}
		}
        stage("check UI login") {
	        when {
			    expression {
			        return (is_smoke == true)
			    }
			}
		    steps {
			    script {
					println "SMOKE TEST: check UI login success"
				}
			}
		}
		
		stage("Integrate-ModelA") {
	        when {
			    expression {
			        return (is_smoke == false)
			    }
			}
		    steps {
			    script {
					println "Integrate-ModelA"
				}
			}
		}
		
		stage("Integrate-ModelB") {
	        when {
			    expression {
			        return (is_smoke == false)
			    }
			}
		    steps {
			    script {
					println "Integrate-ModelB"
				}
			}
		}
	}

}

