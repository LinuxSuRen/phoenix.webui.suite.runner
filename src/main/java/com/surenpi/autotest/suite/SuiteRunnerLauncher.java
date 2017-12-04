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

package com.surenpi.autotest.suite;

import com.beust.jcommander.JCommander;
import com.surenpi.autotest.code.Generator;
import com.surenpi.autotest.code.impl.DefaultXmlCodeGenerator;
import com.surenpi.autotest.compiler.JDTUtils;
import com.surenpi.autotest.suite.cmd.RunnerParam;
import com.surenpi.autotest.suite.parser.SuiteParser;
import com.surenpi.autotest.suite.parser.SuiteParserFactory;
import com.surenpi.autotest.suite.runner.SuiteRunner;
import com.surenpi.autotest.suite.util.Configuration;
import com.surenpi.autotest.suite.util.PageXmlFilter;
import com.surenpi.autotest.suite.util.SuiteUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * @author suren
 * @date 2017年4月26日 下午9:37:26
 */
public class SuiteRunnerLauncher
{
	public static void main(String[] args) throws IOException
	{
		RunnerParam param = new RunnerParam();
		JCommander commander = new JCommander(param, args);
		
		List<URL> urlList = new ArrayList<URL>();
		runnerRead(urlList);
		runnerList.addAll(param.runners);

        if(param.listRunners)
		{
			System.out.println(urlList);
		}

		if(param.compile)
		{
			compile(param);
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

            runFromList(param.runnerParser);
		}
		
		if(param.isHelp)
		{
			commander.usage();
		}
	}

    private static void compile(RunnerParam param)
    {
        URL itemUrl = SuiteRunnerLauncher.class.getResource("/");
        if(itemUrl == null)
        {
            return;
        }
        System.out.println("itemUrl:" + itemUrl);

        String protocol = itemUrl.getProtocol();
        if("file".equals(protocol))
        {
            String file = itemUrl.getFile();
            File[] subFiles = new File(file).listFiles(new PageXmlFilter());

            Generator generator = new DefaultXmlCodeGenerator(){
                @Override
                protected void done()
                {
                    super.done();
                }
            };

            for(File pageFile : subFiles)
            {
                generator.generate(pageFile.getName(), param.compileDstDir);
            }

            JDTUtils utils = new JDTUtils(param.compileDstDir);
            utils.compileAllFile();

            try
            {
                System.out.println(new File(param.compileDstDir).toURI().toURL());
                ClassLoader loader = new URLClassLoader(new URL[]{
                        new File(param.compileDstDir).toURI().toURL()
                }){
                    @Override
                    protected Class<?> findClass(String s) throws ClassNotFoundException
                    {
                        return super.findClass(s);
                    }
                };

                Thread.currentThread().setContextClassLoader(loader);

//                try
//                {
//                    Class<?> obj = Class.forName("com.surenpi.autotest.demo.Hello", true, loader);
//                    System.out.println(obj.getAnnotations().length);
//                    obj = Thread.currentThread().getContextClassLoader().loadClass("com.surenpi.autotest.demo.Hello");
//                    System.out.println(obj);
//                }
//                catch (ClassNotFoundException e)
//                {
//                    e.printStackTrace();
//                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
	 * @param urlList
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	private static void runnerRead(List<URL> urlList) throws UnsupportedEncodingException, IOException
	{
		InputStream inputA = SuiteRunnerLauncher.class.getResourceAsStream("/");
		if(inputA == null)
		{
			return;
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputA));
		String line = null;
		while((line = reader.readLine()) != null)
		{
			URL itemUrl = SuiteRunnerLauncher.class.getResource("/" + line);
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
				if(SuiteUtils.isSuiteXml(content))
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

	private static void runFromList(String runnerParser)
    {
        SuiteParser parser = null;
        try
        {
            parser = SuiteParserFactory.getInstance(runnerParser);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }

        if(parser == null)
        {
            return;
        }

        for(String runner: runnerList)
        {
            SuiteRunner suiteRunner = new SuiteRunner();
            suiteRunner.setSuiteParser(parser);
            suiteRunner.setApplicationClazz(Configuration.class);
            suiteRunner.runFromFileQuietly(new File(runner));
        }
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
//                runFromList();
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
