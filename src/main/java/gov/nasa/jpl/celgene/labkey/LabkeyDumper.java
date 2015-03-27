/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gov.nasa.jpl.celgene.labkey;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.labkey.remoteapi.CommandException;
import org.labkey.remoteapi.Connection;
import org.labkey.remoteapi.query.ContainerFilter;
import org.labkey.remoteapi.query.SelectRowsCommand;
import org.labkey.remoteapi.query.SelectRowsResponse;

import org.apache.commons.lang3.StringEscapeUtils;

public class LabkeyDumper {

	private Connection connection = null;

	private URL labkeyUrl;

	public LabkeyDumper(URL labkeyUrl, String username, String password) {
		this.connection = new Connection(labkeyUrl.toString(), username,
				password);
		this.labkeyUrl = labkeyUrl;

	}

	public void generateJSON(List<Map<String, Object>> studyData) {

		// loop over the returned rows
		System.out.println("{\"studies\":[");
		for (Map<String, Object> study : studyData) {
			System.out.println("{");
			for (String key : study.keySet()) {
				System.out.println("\"" + key + "\" : \"" + StringEscapeUtils.escapeJson(String.valueOf(study.get(key)))
						+ "\",");
			}
			System.out.println("},");
		}
		System.out.println("]}");
	}

	public List<Map<String, Object>> dumpStudies(String projectName) throws IOException,
			CommandException {
		// create a SelectRowsCommand to call the selectRows.api
		SelectRowsCommand cmd = new SelectRowsCommand("study", "Study");
		cmd.setContainerFilter(ContainerFilter.CurrentAndSubfolders);

		// execute the command against the connection
		// within the Api Test project folder
		SelectRowsResponse resp = cmd.execute(this.connection, projectName);
		System.err.println(resp.getRowCount() + " rows were returned.");
		return resp.getRows();
	}

	public static void main(String[] args) throws Exception {
		String url = null;
		String user = null;
		String pass = null;
		String projectName = null;

		String usage = "java LabkeyDumper [--url <url>] [--user <user/email>] [--pass <pass>] [--project <Project Name>]\n";

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--url")) {
				url = args[++i];
			} else if (args[i].equals("--user")) {
				user = args[++i];
			} else if (args[i].equals("--pass")) {
				pass = args[++i];
			}
			else if(args[i].equals("--project")){
				projectName = args[++i];
			}
		}

		if (isEmpty(url) || isEmpty(user) || isEmpty(pass) || isEmpty(projectName)) {
			System.err.println(usage);
			System.exit(1);
		}

		LabkeyDumper dumper = new LabkeyDumper(new URL(url), user, pass);
		dumper.generateJSON(dumper.dumpStudies(projectName));
	}

	private static boolean isEmpty(String string) {
		return string == null || (string != null && string.equals(""));
	}
}
