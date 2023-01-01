%% GENERACION DEL VIDEO DE ENTRADA

% INICIALIZAR LA CAMARA
video = videoinput('winvideo', 1, 'YUY2_320x240');
video.TriggerRepeat=inf;
video.FrameGrabInterval=1;
video.ReturnedColorSpace = 'rgb';


% PARA COMPROBAR LA VELOCIDAD DE TRABAJO DE LA WEBCAM
% ---------------------------------------------------
start(video)
TIEMPO = [];

while(video.FramesAcquired<150)
	[I TIME METADATA]=getdata(video, 1);
	TIEMPO = [TIEMPO; METADATA.AbsTime];
	imshow(I)
end

stop(video);
% ---------------------------------------------------

preview(video);
fpsTrabajoMax = 30;


% CONFIGURACION DE LAS OPCIONES DE LA CAMARA Y EL VIDEO

video.TriggerRepeat=inf;
video.FrameGrabInterval=3; % Queremos 10 fps --> 30fps/3 = 10fps
fpsTrabajo = fpsTrabajoMax/video.FrameGrabInterval;

aviFile = VideoWriter('VideoEntrada.avi', 'Uncompressed AVI');
aviFile.FrameRate = fpsTrabajo;
duracioVideo = 10;
framesVideo = aviFile.FrameRate*duracioVideo;

open(aviFile) % Abrimos el archivo de video para escribir en el


% COMIENZA LA GRABACION

start(video)

while(video.FramesAcquired < framesVideo)
	I = getdata(video, 1);
	writeVideo(aviFile, I);
end

stop(video) % Termina la grabación

close(aviFile) % Cerramos el archivo de video para guardarlo


%% GENERACION DE LAS IMAGENES DE CALIBRACION

% INICIALIZACION DE LA CAMARA
video = videoinput('winvideo', 1, 'YUY2_320x240');
video.ReturnedColorSpace = 'rgb';

preview(video);
imagenes = uint8(zeros(240, 320, 3, 16));

for i=1:16
    I = getsnapshot(video);
    imagenes(:,:,:,i) = I;
    pause;
end

for i=1:16
    imshow(imagenes(:,:,:,i));
    pause;
end

save('./datos/imagenes', 'imagenes')

