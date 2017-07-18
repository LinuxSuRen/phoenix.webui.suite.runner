/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.surenpi.autotest.suite.runner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import com.beust.jcommander.JCommander;


/**
 * @author suren
 * @date 2017年4月26日 下午9:37:26
 */
public class SuiteRunnerLuancher
{
	public static void main(String[] args) throws IOException
	{
		RunnerParam param = new RunnerParam();
		JCommander commander = new JCommander(param, args);
		
		List<URL> urlList = new ArrayList<URL>();
		runnerRead(urlList);
		
		if(param.listRunners)
		{
			System.out.println(urlList);
		}
		
		if(param.interactive)
		{
			String[] interArgs = new String[args.length - 1];
			for(int i = 0; i < interArgs.length; i++)
			{
				if(!"-inter".equals(args[i]))
				{
					interArgs[i] = args[i];
				}
			}
			
//			Bootstrap.main(interArgs);
		}
		else if(param.isGuiMode)
		{
			gui(urlList);
		}
		else if(param.download)
		{
		}
		else
		{
			param.isHelp = true;
		}
		
		if(param.isHelp)
		{
			commander.usage();
		}
	}
	
	/**
	 * @param urlList
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	private static void runnerRead(List<URL> urlList) throws UnsupportedEncodingException, IOException
	{
		InputStream inputA = SuiteRunnerLuancher.class.getResourceAsStream("/");
		if(inputA == null)
		{
			return;
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputA));
		String line = null;
		while((line = reader.readLine()) != null)
		{
			URL itemUrl = SuiteRunnerLuancher.class.getResource("/" + line);
			if(itemUrl == null)
			{
				continue;
			}
			String path = URLDecoder.decode(itemUrl.getFile(), "utf-8");
			if(!path.endsWith(".xml"))
			{
				continue;
			}
			
			try(InputStream input = itemUrl.openStream())
			{
				byte[] content = IOUtils.toByteArray(input);
				if(SuiteUtil.isSuiteXml(content))
				{
					urlList.add(itemUrl);
				}
			}
		}
	}

	private static void gui(List<URL> urlList)
	{
		JFrame frame = new JFrame("PhoenixFramework");
		
		JPanel centerPanel = new JPanel();
		frame.add(centerPanel);
		centerPanel.setLayout(new BorderLayout());
		
		createItemsPanel(centerPanel, urlList);
		createButPanel(centerPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(600, 400);
	}

	/**
	 * @param centerPanel
	 */
	private static void createButPanel(JPanel centerPanel)
	{
		JPanel butPanel = new JPanel();
		centerPanel.add(butPanel, BorderLayout.SOUTH);
		
		JButton execBut = new JButton("Exec");
		butPanel.add(execBut);
		execBut.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(String runner: runnerList)
				{
					try
					{
						new SuiteRunner().runFromClasspathFile(runner);
					}
					catch (NoSuchFieldException | SecurityException
							| IllegalArgumentException | IllegalAccessException
							| IOException | DocumentException
							| InterruptedException | SAXException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	private static List<String> runnerList = new ArrayList<String>();

	/**
	 * @param centerPanel
	 * @param urlList
	 */
	private static void createItemsPanel(JPanel centerPanel, List<URL> urlList)
	{
		JPanel itemsPanel = new JPanel();
		centerPanel.add(itemsPanel, BorderLayout.CENTER);
		
		if(CollectionUtils.isEmpty(urlList))
		{
			return;
		}
		
		for(URL url : urlList)
		{
			String text = url.getFile();
			JCheckBox box = new JCheckBox(new File(text).getName());
			box.addActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					JCheckBox source = (JCheckBox) e.getSource();
					if(source.isSelected())
					{
						runnerList.add(source.getText());
					}
					else
					{
						runnerList.remove(source.getText());
					}
				}
			});
			
			itemsPanel.add(box);
		}
	}
}
