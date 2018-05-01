package application;

import java.io.File;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.emf.codegen.ecore.generator.Generator;

public class CodeGenerator {

	public static void main(String[] args) throws Exception {

		if (args.length != 1) {
			System.out.println("Input an Ecore model as argument.");
			return;
		}

		CodeGenerator myApplication = new CodeGenerator();
		myApplication.generate(new File(args[0]));

	}

	public CodeGenerator() {}

	protected File outputDirectory = null;

	public void generate(File inputFile) throws Exception {

		if (inputFile.getName().endsWith(".ecore")) {
			EmfModel model = new EmfModel();
			model.setName("Ecore");
			EPackage.Registry.INSTANCE.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
			model.setMetamodelUri(EcorePackage.eINSTANCE.getNsURI());
			model.setModelFile(inputFile.getAbsolutePath());
			model.load();
			generate(model, inputFile.getParentFile());

			model.disposeModel();
		}
	}

	protected void generate(EmfModel model, File directory) throws Exception {

		EglFileGeneratingTemplateFactory templateFactory = new EglFileGeneratingTemplateFactory();
		templateFactory.setOutputRoot(directory.getAbsolutePath());

		EgxModule module = new EgxModule(templateFactory);
		module.parse(CodeGenerator.class.getResource("codeGen.egx").toURI());
		module.getContext().getModelRepository().addModel(model);

		module.execute();
		module.getContext().getModelRepository().dispose();
	}
}
